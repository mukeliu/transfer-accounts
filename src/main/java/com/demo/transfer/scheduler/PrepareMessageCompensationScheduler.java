package com.demo.transfer.scheduler;

import com.demo.transfer.common.BaseEvent;
import com.demo.transfer.common.MqMessageStatus;
import com.demo.transfer.common.MqTemplate;
import com.demo.transfer.domain.model.account.DeductAccountEvent;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.model.transfer.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.stereotype.Component;

import static com.demo.transfer.common.MqMessageStatus.DONE;
import static com.demo.transfer.domain.model.transfer.TransferStatus.DEDUCTED;
import static com.demo.transfer.domain.model.transfer.TransferStatus.FAILED;

/**
 * description: 补偿扣款时，步骤 3 发送确认消息丢失的情况，主要思路为：
 * 定时扫描队列中 prepare 消息，并询问交易记录是否已扣款
 * 对于已扣款的交易，将消息状态更新为 done
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Component
public class PrepareMessageCompensationScheduler implements Scheduler {
    private final MqTemplate mqTemplate;
    private final TransferRecordRepository transferRecordRepository;

    public PrepareMessageCompensationScheduler(MqTemplate mqTemplate,
                                               TransferRecordRepository transferRecordRepository) {
        this.mqTemplate = mqTemplate;
        this.transferRecordRepository = transferRecordRepository;
    }

    public void execute() {
        // 拉取所有 prepare mq消息
        mqTemplate.retrieveMqMessages(BaseEvent.class, MqMessageStatus.PREPARE)
            .forEach(mqMessage -> {
                String orderSeq = ((DeductAccountEvent) mqMessage.getPayload()).getTransferRecord().getOrderSeq();
                TransferRecord transferRecord = transferRecordRepository.findByOrderSeq(orderSeq);
                TransferStatus transferStatus = transferRecord.getStatus();
                // transferStatus == FAILED ： 表示在扣款过程中失败，此消息已无用，删除
                // transferStatus == DEDUCTED： 表示扣款成功，但是确认消息丢失，所以将该消息再次确认
                if (transferStatus == FAILED) {
                    mqTemplate.deleteMessage(mqMessage.getAddress());
                } else if (transferStatus == DEDUCTED) {
                    mqTemplate.overrideMessage(mqMessage.getAddress(), new DeductAccountEvent(DONE, transferRecord));
                }
            });
    }
}
