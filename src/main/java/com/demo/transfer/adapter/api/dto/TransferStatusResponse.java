package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.transfer.TransferStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * description: 查询交易状态接口响应 <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@ApiModel("查询交易状态Response")
public class TransferStatusResponse {

    @ApiModelProperty(value = "交易流水号", name = "orderSeq")
    private String orderSeq;

    @ApiModelProperty(value = "交易状态", name = "transferStatus")
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
