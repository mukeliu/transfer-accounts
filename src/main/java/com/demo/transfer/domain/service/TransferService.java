package com.demo.transfer.domain.service;

import com.demo.transfer.common.MqTemplate;
import com.demo.transfer.domain.exception.AccountNotFoundException;
import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.factory.TransferRecordFactory;
import com.demo.transfer.domain.model.Account;
import com.demo.transfer.domain.model.DeductAccountEvent;
import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.repository.AccountRepository;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.demo.transfer.common.MqMessageStatus.DONE;
import static com.demo.transfer.common.MqMessageStatus.PREPARE;
import static com.demo.transfer.domain.model.TransferStatus.SUCCEED;

@Service
public class TransferService {

    private final AccountRepository accountRepository;
    private final TransferRecordRepository transferRecordRepository;
    private final MqTemplate mqTemplate;

    @Autowired
    public TransferService(AccountRepository accountRepository,
                           TransferRecordRepository transferRecordRepository,
                           MqTemplate mqTemplate) {
        this.accountRepository = accountRepository;
        this.transferRecordRepository = transferRecordRepository;
        this.mqTemplate = mqTemplate;
    }

    public Transfer doTransfer(Transfer transfer) {
        // 以交易流水号为唯一索引，插入去重表，防止重复扣款
        TransferRecord transferRecord;
        try {
            transferRecord = generateTransferRecord(transfer);
        } catch (Exception e) {
            return transfer;
        }

        Account payerAccount = Optional.ofNullable(accountRepository.findByNumber(transfer.getPayerAccountNumber()))
            .orElseThrow(() -> new AccountNotFoundException(transfer, transfer.getPayerAccountNumber()));
        Account payeeAccount = Optional.ofNullable(accountRepository.findByNumber(transfer.getPayeeAccountNumber()))
            .orElseThrow(() -> new AccountNotFoundException(transfer, transfer.getPayeeAccountNumber()));

        // 账户预检查，包括收/扣款账户姓名、状态、余额等等
        preCheck(payerAccount, payeeAccount, transfer);

        // 扣款操作，开启事务
        minusBalance(payerAccount, transferRecord, transfer.getAmount());

        transfer.setEndTime(LocalDateTime.now());
        return transfer;
    }

    private TransferRecord generateTransferRecord(Transfer transfer) {
        TransferRecord transferRecord = TransferRecordFactory.create(transfer);
        transferRecordRepository.save(transferRecord);
        return transferRecord;
    }

    private void preCheck(Account payerAccount, Account payeeAccount, Transfer transfer) {
        if (!payerAccount.isNormal()) {
            throw new TransferException(transfer, String.format("账户状态[%s]不支持转账", payerAccount.getStatus()));
        }

        if (!payeeAccount.isNormal()) {
            throw new TransferException(transfer, String.format("账户状态[%s]不支持转账", payeeAccount.getStatus()));
        }

        if (!Objects.equals(payerAccount.getName(), transfer.getPayerName())) {
            throw new TransferException(transfer, "扣款人账户姓名不匹配");
        }

        if (!Objects.equals(payeeAccount.getName(), transfer.getPayeeName())) {
            throw new TransferException(transfer, "收款人账户姓名不匹配");
        }
        if (!payerAccount.isBalanceSufficient(transfer.getAmount())) {
            throw new TransferException(transfer, "余额不足");
        }
    }

    /**
     * description: 扣款操作，主要分为 3 个步骤：
     * 步骤1： 先向队列发送 prepare 消息，通知准备扣款，并拿到消息在队列中的地址，
     * prepare 消息对用户不可见
     * 如果发送失败，直接转账失败，成功进入 步骤 2；
     * 步骤2： 执行本地扣款操作，并更新交易状态，本地事务可以保证操作的原子性；
     * 步骤3： 用步骤 1 的消息地址，更改消息状态为 done，表示可以被消费
     *
     * @param payerAccount：   扣款账户
     * @param transferRecord： 交易记录
     * @param amount：交易金额
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public void minusBalance(Account payerAccount, TransferRecord transferRecord, BigDecimal amount) {
        String messageAddress = mqTemplate.syncSend(new DeductAccountEvent(PREPARE, transferRecord));
        if (messageAddress == null) {
            throw new TransferException(transferRecord, "扣款失败");
        }
        accountRepository.minusAmount(payerAccount, amount);
        transferRecordRepository.updateTransferStatus(transferRecord, SUCCEED);
        mqTemplate.overrideMessage(messageAddress, new DeductAccountEvent(DONE, transferRecord));
    }

    public boolean receipt(TransferRecord transferRecord) {
        try {
            Account payeeAccount = accountRepository.findByNumber(transferRecord.getPayeeAccountNumber());
            if (payeeAccount == null || !payeeAccount.isNormal()) {
                return false;
            }
            addBalance(payeeAccount, transferRecord);
        } catch (Exception e) {
            return false;
        }
        return true;
    }

    /**
     * description: 收款人账户加钱，同样利用交易流水线的唯一性，保障不会因为重试而被多次加钱
     *
     * @param payeeAccount： 收款账户
     * @param transferRecord： 交易记录
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public void addBalance(Account payeeAccount, TransferRecord transferRecord) {
        accountRepository.addBalance(payeeAccount, transferRecord.getAmount());
        transferRecord.setEndTime(LocalDateTime.now());
        transferRecord.setStatus(SUCCEED);
        transferRecordRepository.save(transferRecord);
    }

    public void reverse(TransferRecord transferRecord) {

    }
}

