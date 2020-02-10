package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.model.transfer.TransferStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * description: 转账结果响应 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@ApiModel("转账结果Response")
public class TransferResponse {

    @ApiModelProperty(value = "交易流水号", name = "orderSeq")
    private String orderSeq;

    @ApiModelProperty(value = "付款人账户号", name = "payerAccountNumber")
    private String payerAccountNumber;

    @ApiModelProperty(value = "付款人姓名", name = "payerName")
    private String payerName;

    @ApiModelProperty(value = "收款人账户号", name = "payeeAccountNumber")
    private String payeeAccountNumber;

    @ApiModelProperty(value = "收款人姓名", name = "payeeName")
    private String payeeName;

    @ApiModelProperty(value = "转账金额", name = "amount")
    private BigDecimal amount;

    @ApiModelProperty(value = "转账开始时间", name = "beginTime")
    private LocalDateTime beginTime;

    @ApiModelProperty(value = "转账结束时间", name = "endTime")
    private LocalDateTime endTime;

    @ApiModelProperty(value = "交易状态", name = "status")
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
