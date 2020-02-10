package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.transfer.Transfer;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import javax.validation.constraints.Digits;
import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import java.math.BigDecimal;

/**
 * description: 转账请求入参 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@ApiModel("转账请求入参")
public class TransferRequest {

    @ApiModelProperty(value = "密钥", name = "secret")
    @NotEmpty(message = "密码不能为空")
    private String secret;

    @ApiModelProperty(value = "交易流水号", name = "orderSeq")
    @NotEmpty(message = "交易单号不能为空")
    private String orderSeq;

    @ApiModelProperty(value = "交易金额", name = "amount")
    @NotNull(message = "交易金额不能为空")
    @Digits(integer = 11, fraction = 2, message = "整数部分不超过11位,小数点后不超过2位")
    private BigDecimal amount;

    @ApiModelProperty(value = "付款人账号", name = "payerAccountNumber")
    @NotEmpty(message = "付款账号不能为空")
    private String payerAccountNumber;

    @ApiModelProperty(value = "付款人姓名", name = "payerName")
    @NotEmpty(message = "付款人姓名不能为空")
    private String payerName;

    @ApiModelProperty(value = "收款人账号", name = "payeeAccountNumber")
    @NotEmpty(message = "收款账号不能为空")
    private String payeeAccountNumber;

    @ApiModelProperty(value = "收款人姓名", name = "payeeName")
    @NotEmpty(message = "收款人姓名不能为空")
    private String payeeName;

    @ApiModelProperty(value = "转账留言", name = "comment")
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
