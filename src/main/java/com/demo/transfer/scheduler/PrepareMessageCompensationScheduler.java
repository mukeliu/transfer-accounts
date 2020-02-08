package com.demo.transfer.scheduler;

import com.demo.transfer.common.BaseEvent;
import com.demo.transfer.common.MqMessageStatus;
import com.demo.transfer.common.MqTemplate;
import com.demo.transfer.domain.model.DeductAccountEvent;
import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.stereotype.Component;

import static com.demo.transfer.common.MqMessageStatus.DONE;

/**
 * description: 补偿扣款时，步骤 3 发送确认消息丢失的情况，主要思路为：
 * 定时扫描队列中 prepare 消息，并询问交易记录是否已扣款
 * 对于已扣款的交易，将消息状态更新为 done
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Component
public class PrepareMessageCompensationScheduler {
    private final MqTemplate mqTemplate;
    private final TransferRecordRepository transferRecordRepository;

    public PrepareMessageCompensationScheduler(MqTemplate mqTemplate,
                                               TransferRecordRepository transferRecordRepository) {
        this.mqTemplate = mqTemplate;
        this.transferRecordRepository = transferRecordRepository;
    }


    public void execute() {
        mqTemplate.retrieveMqMessages(BaseEvent.class, MqMessageStatus.PREPARE)
            .forEach(mqMessage -> {
                String orderSeq = ((DeductAccountEvent) mqMessage.getPayload()).getTransferRecord().getOrderSeq();
                transferRecordRepository.findByOrderSeq(orderSeq)
                    .filter(transferRecord -> transferRecord.getStatus() == TransferStatus.DEDUCTED)
                    .ifPresent(transferRecord -> {
                        mqTemplate.overrideMessage(mqMessage.getAddress(), new DeductAccountEvent(DONE, transferRecord));
                    });
            });
    }
}
