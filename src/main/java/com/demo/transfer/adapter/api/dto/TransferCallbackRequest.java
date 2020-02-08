package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.TransferStatus;

/**
 * description: TransferCallbackRequest <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class TransferCallbackRequest {
    private String orderSeq;
    private TransferStatus status;

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }
}
