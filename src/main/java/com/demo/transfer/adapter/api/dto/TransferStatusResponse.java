package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.TransferStatus;

/**
 * description: TransferStatusResponse <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class TransferStatusResponse {
    private String orderSeq;
    private TransferStatus transferStatus;

    public TransferStatusResponse(String orderSeq, TransferStatus transferStatus) {
        this.orderSeq = orderSeq;
        this.transferStatus = transferStatus;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public TransferStatus getTransferStatus() {
        return transferStatus;
    }

    public void setTransferStatus(TransferStatus transferStatus) {
        this.transferStatus = transferStatus;
    }
}
