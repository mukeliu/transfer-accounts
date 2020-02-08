package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.Transfer;

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

    public static TransferResponse from(Transfer transfer) {
        TransferResponse transferResponse = new TransferResponse();
        transferResponse.setOrderSeq(transfer.getOrderSeq());
        transferResponse.setAmount(transfer.getAmount());
        transferResponse.setPayerAccountNumber(transfer.getPayerAccountNumber());
        transferResponse.setPayerName(transfer.getPayerName());
        transferResponse.setPayeeAccountNumber(transfer.getPayeeAccountNumber());
        transferResponse.setPayeeName(transfer.getPayeeName());
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
}
