package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferResponse {
    private String orderSeq;
    private String payerAccountNumber;
    private String payerName;
    private String payeeAccountNumber;
    private String payeeName;
    private BigDecimal amount;
    private LocalDateTime beginTime;
    private LocalDateTime endTime;
    private TransferStatus status;

    public static TransferResponse from(TransferRecord transferRecord) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setOrderSeq(transferRecord.getOrderSeq());
        transferResponse.setAmount(transferRecord.getAmount());
        transferResponse.setPayerAccountNumber(transferRecord.getPayerAccountNumber());
        transferResponse.setPayerName(transferRecord.getPayerName());
        transferResponse.setPayeeAccountNumber(transferRecord.getPayeeAccountNumber());
        transferResponse.setPayeeName(transferRecord.getPayeeName());
        transferResponse.setStatus(transferRecord.getStatus());
        transferResponse.setBeginTime(transferRecord.getBeginTime());
        transferResponse.setEndTime(transferRecord.getEndTime());
        return transferResponse;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getPayerAccountNumber() {
        return payerAccountNumber;
    }

    public void setPayerAccountNumber(String payerAccountNumber) {
        this.payerAccountNumber = payerAccountNumber;
    }

    public String getPayerName() {
        return payerName;
    }

    public void setPayerName(String payerName) {
        this.payerName = payerName;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public LocalDateTime getBeginTime() {
        return beginTime;
    }

    public void setBeginTime(LocalDateTime beginTime) {
        this.beginTime = beginTime;
    }

    public LocalDateTime getEndTime() {
        return endTime;
    }

    public void setEndTime(LocalDateTime endTime) {
        this.endTime = endTime;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
    }
}
