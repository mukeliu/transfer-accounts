package com.demo.transfer.domain.service;

import com.demo.transfer.common.MqTemplate;
import com.demo.transfer.domain.factory.TransferRecordFactory;
import com.demo.transfer.domain.model.account.Account;
import com.demo.transfer.domain.model.account.DeductAccountEvent;
import com.demo.transfer.domain.model.transfer.Transfer;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.repository.AccountRepository;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

import static com.demo.transfer.common.MqMessageStatus.DONE;
import static com.demo.transfer.common.MqMessageStatus.PREPARE;
import static com.demo.transfer.domain.model.transfer.TransferStatus.*;

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

    /**
     * description: 创建转账记录，防止重复转账，以 orderSeq 作为唯一约束
     * 如果抛 DuplicateKeyException 异常，表明库中已经存在该记录，直接
     * 查询返回库中的转账记录
     *
     * @param transfer： 转账对象，包含转账相关信息
     * @return: com.demo.transfer.domain.model.transfer.TransferRecord
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    public TransferRecord findExistedOrCreateTransferRecord(Transfer transfer) {
        try {
            TransferRecord transferRecord = TransferRecordFactory.create(transfer);
            transferRecordRepository.save(transferRecord);
            return transferRecord;
        } catch (DuplicateKeyException e) {
            return transferRecordRepository.findByOrderSeq(transfer.getOrderSeq());
        }
    }

    /**
     * description: 转账失败操作 <br>
     *
     * @param transferRecord： 转账记录
     * @param failedMessage:  失败信息
     * @return: void
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    public void fail(TransferRecord transferRecord, String failedMessage) {
        transferRecord.setFailedMessage(failedMessage);
        transferRecord.setEndTime(LocalDateTime.now());
        transferRecordRepository.updateTransferStatus(transferRecord, transferRecord.getStatus(), FAILED);
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
            return;
        }
        // 采用乐观锁以version字段作为区分，如果更新的行数为0，则表示转账失败
        boolean success = accountRepository.minusAmount(payerAccount, transferRecord.getAmount())
            && transferRecordRepository.updateTransferStatus(transferRecord, transferRecord.getStatus(), SUCCEED);
        if (!success) {
            fail(transferRecord, "预款失败");
            return;
        }
        mqTemplate.overrideMessage(messageAddress, new DeductAccountEvent(DONE, transferRecord));
        transferRecordRepository.updateTransferStatus(transferRecord, BEGIN, DEDUCTED);
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

