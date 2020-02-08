package com.demo.transfer.adapter.mq;

import com.demo.transfer.application.TransferEventHandler;
import com.demo.transfer.domain.model.DeductAccountEvent;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.model.TransferType;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

import static com.demo.transfer.domain.model.TransferStatus.FAILED;

/**
 * description: TransferEventListener <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Component
public class TransferEventListener {

    private final TransferEventHandler transferEventHandler;
    private final TransferRecordRepository transferRecordRepository;

    // 最大重试次数
    private static final int MAX_RETRY_TIMES = 10;
    // 重试间隔
    private static final long RETRY_INTERVAL = 2000;

    public TransferEventListener(TransferEventHandler transferEventHandler,
                                 TransferRecordRepository transferRecordRepository) {
        this.transferEventHandler = transferEventHandler;
        this.transferRecordRepository = transferRecordRepository;
    }

    @EventListener
    public void onPayerAccountDeducted(DeductAccountEvent deductAccountEvent) {
        int retryTimes = 0;
        boolean success = false;
        TransferRecord transferRecord = deductAccountEvent.getTransferRecord();
        transferRecord.setType(TransferType.RECEIPT);
        while (retryTimes < MAX_RETRY_TIMES) {
            success = transferEventHandler.receipt(transferRecord);
            if (success) {
                break;
            }
            retryTimes++;
            try {
                Thread.sleep(RETRY_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
        /*
         *  重试超过最大次数仍然收款失败，置交易状态为失败, 后面可以通过定时任务做冲正处理
         */
        if (!success) {
            transferRecord.setStatus(FAILED);
            transferRecord.setEndTime(LocalDateTime.now());
            transferRecordRepository.save(transferRecord);
        }
    }
}
