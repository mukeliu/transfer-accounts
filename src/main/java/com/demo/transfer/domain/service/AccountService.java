package com.demo.transfer.domain.service;

import com.demo.transfer.common.MqTemplate;
import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.factory.AccountRecordFactory;
import com.demo.transfer.domain.model.account.Account;
import com.demo.transfer.domain.model.account.AccountTransferType;
import com.demo.transfer.domain.model.account.DeductAccountEvent;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.repository.AccountRepository;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;

import static com.demo.transfer.common.MqMessageStatus.DONE;
import static com.demo.transfer.common.MqMessageStatus.PREPARE;
import static com.demo.transfer.domain.model.transfer.TransferStatus.DEDUCTED;
import static com.demo.transfer.domain.model.transfer.TransferStatus.RECEIVING;

/**
 * description: 账务服务，处理收款与扣款 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Service
public class AccountService {
    private final AccountRepository accountRepository;
    private final TransferRecordRepository transferRecordRepository;
    private final MqTemplate mqTemplate;

    @Autowired
    public AccountService(AccountRepository accountRepository,
                          TransferRecordRepository transferRecordRepository,
                          MqTemplate mqTemplate) {
        this.accountRepository = accountRepository;
        this.transferRecordRepository = transferRecordRepository;
        this.mqTemplate = mqTemplate;
    }

    /**
     * description: 扣款操作，主要分为 3 个步骤：
     * 步骤1： 先向队列发送 prepare 消息，通知准备扣款，并拿到消息在队列中的地址，
     * prepare 消息对用户不可见
     * 如果发送失败，直接转账失败，成功进入 步骤 2；
     * 步骤2： 执行本地扣款操作，并更新交易状态，本地事务可以保证操作的原子性；
     * 步骤3： 用步骤 1 的消息地址，更改消息状态为 done，表示可以被消费
     *
     * @param payerAccount ：   扣款账户
     * @param orderSeq     ： 交易流水号
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public boolean minusBalance(Account payerAccount, String orderSeq) {
        TransferRecord transferRecord = transferRecordRepository.findByOrderSeq(orderSeq);

        // 插入去重表，防止重复扣款。
        try {
            accountRepository.saveAccountRecord(AccountRecordFactory.create(transferRecord, AccountTransferType.MINUS));
        } catch (DuplicateKeyException e) {
            return true;
        }

        String messageAddress = mqTemplate.syncSend(new DeductAccountEvent(PREPARE, transferRecord));
        if (messageAddress == null) {
            throw new TransferException("预扣款失败");
        }
        // 采用乐观锁以version字段作为区分，如果更新的行数为0，则表示转账失败
        boolean success = accountRepository.minusAmount(payerAccount, transferRecord.getAmount());
        if (!success) {
            throw new TransferException("预扣款失败");
        }
        // 发送扣款确认消息
        mqTemplate.overrideMessage(messageAddress, new DeductAccountEvent(DONE, transferRecord));
        return true;
    }

    /**
     * description: 收款操作 <br>
     *
     * @param transferRecord：交易记录
     * @return: boolean
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public boolean addBalance(TransferRecord transferRecord) {
        try {
            accountRepository.saveAccountRecord(AccountRecordFactory.create(transferRecord, AccountTransferType.ADD));
        } catch (DuplicateKeyException e) {
            return true;
        }

        Account payeeAccount = accountRepository.findByNumber(transferRecord.getPayeeAccountNumber());
        if (payeeAccount == null || !payeeAccount.isNormal()) {
            return false;
        }
        // 调用远程收款
        boolean success = accountRepository.addBalance(payeeAccount, transferRecord.getAmount());
        if (!success) {
            throw new TransferException("收款失败");
        }
        transferRecordRepository.updateTransferStatus(transferRecord, DEDUCTED, RECEIVING);
        return true;
    }
}
