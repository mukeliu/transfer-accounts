package com.demo.transfer.adapter.mq;

import com.demo.transfer.application.TransferEventHandler;
import com.demo.transfer.domain.model.DeductAccountEvent;
import com.demo.transfer.domain.model.TransferRecord;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * description: TransferEventListener <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Component
public class TransferEventListener {

    private final TransferEventHandler transferEventHandler;

    // 最大重试次数
    private static final int MAX_RETRY_TIMES = 10;
    // 重试间隔
    private static final long RETRY_INTERVAL = 2000;

    public TransferEventListener(TransferEventHandler transferEventHandler) {
        this.transferEventHandler = transferEventHandler;
    }

    @EventListener
    public void onPayerAccountDeducted(DeductAccountEvent deductAccountEvent) {
        int retryTimes = 0;
        TransferRecord transferRecord = deductAccountEvent.getTransferRecord();
        while (retryTimes < MAX_RETRY_TIMES) {
            if (transferEventHandler.receipt(transferRecord.getOrderSeq())) {
                break;
            }
            retryTimes++;
            try {
                Thread.sleep(RETRY_INTERVAL);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }
}
