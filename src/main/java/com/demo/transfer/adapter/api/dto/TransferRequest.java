package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.model.TransferRecord;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

public class TransferRequest {

    /**
     * 加密信息,用于安全验证
     */
    @NotEmpty(message = "请求非法")
    private String secret;

    /**
     * 交易单号
     */
    @NotEmpty(message = "交易单号不能为空")
    private String orderSeq;

    /**
     * 交易金额
     */
    @NotNull(message = "交易金额不能为空")
    @Digits(integer = 11, fraction = 2, message = "整数部分不超过11位,小数点后不超过2位")
    private BigDecimal amount;

    /**
     * 付款人账号
     */
    @NotEmpty(message = "付款账号不能为空")
    private String payerAccountNumber;

    /**
     * 付款人姓名
     */
    @NotEmpty(message = "付款人姓名不能为空")
    private String payerName;

    /**
     * 收款人账号
     */
    @NotEmpty(message = "收款账号不能为空")
    private String payeeAccountNumber;

    /**
     * 收款人账号
     */
    @NotEmpty(message = "收款人姓名不能为空")
    private String payeeName;

    /**
     * 备注
     */
    private String comment;

    public Transfer toTransfer() {
        Transfer transfer = new Transfer();
        transfer.setOrderSeq(orderSeq);
        transfer.setAmount(amount);
        transfer.setPayerAccountNumber(payerAccountNumber);
        transfer.setPayerName(payerName);
        transfer.setPayeeAccountNumber(payeeAccountNumber);
        transfer.setPayeeName(payeeName);
        transfer.setComment(comment);
        return transfer;
    }


    public String getSecret() {
        return secret;
    }

    public void setSecret(String secret) {
        this.secret = secret;
    }

    public String getOrderNo() {
        return orderSeq;
    }

    public void setOrderNo(String orderNo) {
        this.orderSeq = orderNo;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public String getPayerAccountNumber() {
        return payerAccountNumber;
    }

    public void setPayerAccountNumber(String payerAccountNumber) {
        this.payerAccountNumber = payerAccountNumber;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
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
}
