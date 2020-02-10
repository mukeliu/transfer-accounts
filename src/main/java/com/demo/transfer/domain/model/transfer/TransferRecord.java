package com.demo.transfer.domain.model.transfer;

import java.math.BigDecimal;
import java.time.LocalDateTime;

public class TransferRecord {

    /**
     * 交易记录 id
     */
    private String id;
    /**
     * 交易流水号
     */
    private String orderSeq;
    /**
     * 付款人账号
     */
    private String payerAccountNumber;
    /**
     * 付款人姓名
     */
    private String payerName;
    /**
     * 收款人账号
     */
    private String payeeAccountNumber;
    /**
     * 收款人姓名
     */
    private String payeeName;
    /**
     * 转账金额
     */
    private BigDecimal amount;
    /**
     * 转账附言
     */
    private String comment;
    /**
     * 交易状态
     */
    private TransferStatus status;
    /**
     * 交易失败信息
     */
    private String failedMessage;
    /**
     * 交易发起时间
     */
    private LocalDateTime beginTime;
    /**
     * 交易结束时间
     */
    private LocalDateTime endTime;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
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

    public String getPayeeName() {
        return payeeName;
    }

    public void setPayeeName(String payeeName) {
        this.payeeName = payeeName;
    }

    public String getComment() {
        return comment;
    }

    public void setComment(String comment) {
        this.comment = comment;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public TransferStatus getStatus() {
        return status;
    }

    public void setStatus(TransferStatus status) {
        this.status = status;
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

    public String getFailedMessage() {
        return failedMessage;
    }

    public void setFailedMessage(String failedMessage) {
        this.failedMessage = failedMessage;
    }

}
