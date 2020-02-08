package com.demo.transfer.domain.service;

import com.demo.transfer.common.MqTemplate;
import com.demo.transfer.domain.exception.AccountNotFoundException;
import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.factory.TransferRecordFactory;
import com.demo.transfer.domain.model.*;
import com.demo.transfer.domain.repository.AccountRepository;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.Objects;
import java.util.Optional;

import static com.demo.transfer.common.MqMessageStatus.DONE;
import static com.demo.transfer.common.MqMessageStatus.PREPARE;
import static com.demo.transfer.domain.model.TransferStatus.*;

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

    public TransferRecord transfer(Transfer transfer) {
        TransferRecord transferRecord = createTransferRecord(transfer);
        // async
        doTransfer(transferRecord);
        return transferRecord;
    }

    private TransferRecord createTransferRecord(Transfer transfer) {
        TransferRecord transferRecord = TransferRecordFactory.create(transfer);
        transferRecordRepository.save(transferRecord);
        return transferRecord;
    }

    @Async
    public void doTransfer(TransferRecord transferRecord) {
        Account payerAccount = Optional.ofNullable(accountRepository.findByNumber(transferRecord.getPayerAccountNumber()))
            .orElseThrow(() -> new AccountNotFoundException(transferRecord, transferRecord.getPayerAccountNumber()));
        Account payeeAccount = Optional.ofNullable(accountRepository.findByNumber(transferRecord.getPayeeAccountNumber()))
            .orElseThrow(() -> new AccountNotFoundException(transferRecord, transferRecord.getPayeeAccountNumber()));

        // 账户预检查，包括收/扣款账户姓名、状态、余额等等
        try {
            preCheck(payerAccount, payeeAccount, transferRecord);
        } catch (TransferException e) {
            fail(transferRecord, e.getMessage());
        }

        // 扣款操作，开启事务
        minusBalance(payerAccount, transferRecord);

    }

    private void preCheck(Account payerAccount, Account payeeAccount, TransferRecord transferRecord) {
        if (!payerAccount.isNormal()) {
            throw new TransferException(String.format("账户状态[%s]不支持转账", payerAccount.getStatus()));
        }

        if (!payeeAccount.isNormal()) {
            throw new TransferException(String.format("账户状态[%s]不支持转账", payeeAccount.getStatus()));
        }

        if (!Objects.equals(payerAccount.getName(), transferRecord.getPayerName())) {
            throw new TransferException("扣款人账户姓名不匹配");
        }

        if (!Objects.equals(payeeAccount.getName(), transferRecord.getPayeeName())) {
            throw new TransferException("收款人账户姓名不匹配");
        }

        if (!payerAccount.isBalanceSufficient(transferRecord.getAmount())) {
            throw new TransferException("余额不足");
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
     * @param transferRecord： 转账记录
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public void minusBalance(Account payerAccount, TransferRecord transferRecord) {
        String messageAddress = mqTemplate.syncSend(new DeductAccountEvent(PREPARE, transferRecord));
        if (messageAddress == null) {
            fail(transferRecord, "预扣款失败");
        }
        // 采用乐观锁以version字段作为区分，如果更新的行数为0，则表示转账失败
        boolean success = accountRepository.minusAmount(payerAccount, transferRecord.getAmount())
            && transferRecordRepository.updateTransferStatus(transferRecord, SUCCEED);
        if (!success) {
            fail(transferRecord, "预款失败");
        }
        mqTemplate.overrideMessage(messageAddress, new DeductAccountEvent(DONE, transferRecord));
        transferRecordRepository.updateTransferStatus(transferRecord, DEDUCTED);
    }

    private void fail(TransferRecord transferRecord, String failedMessage) {
        transferRecord.setFailedMessage(failedMessage);
        transferRecord.setEndTime(LocalDateTime.now());
        transferRecordRepository.updateTransferStatus(transferRecord, FAILED);
    }

    public boolean receipt(TransferRecord transferRecord) {
        try {
            Account payeeAccount = accountRepository.findByNumber(transferRecord.getPayeeAccountNumber());
            if (payeeAccount == null || !payeeAccount.isNormal()) {
                return false;
            }
            // 调用远程收款
            accountRepository.addBalance(payeeAccount, transferRecord.getAmount());
            transferRecordRepository.updateTransferStatus(transferRecord, RECEIVING);
            return true;
        } catch (Exception e) {
            return false;
        }
    }

    /**
     * description: 冲正转账
     *
     * @param transferRecord： 上一次的转账记录
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    public void reverse(TransferRecord transferRecord) {
        Account payerAccount = accountRepository.findByNumber(transferRecord.getPayerAccountNumber());
        accountRepository.addBalance(payerAccount, transferRecord.getAmount());
    }

}

