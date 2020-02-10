package com.demo.transfer.adapter.mq;

import com.demo.transfer.application.TransferEventHandler;
import com.demo.transfer.domain.model.account.DeductAccountEvent;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

/**
 * description: 转账事件监听器，用于监听接收付款账户付款完成的事件，从而进行接下来的收款流程 <br>
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

    /**
     * description: 处理扣款完成事件，进入收款流程 <br>
      * @param deductAccountEvent： 扣款事件
     * @return: void
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    @EventListener
    public void onPayerAccountDeducted(DeductAccountEvent deductAccountEvent) {
        int retryTimes = 0;
        // 从消息中获取交易记录
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
