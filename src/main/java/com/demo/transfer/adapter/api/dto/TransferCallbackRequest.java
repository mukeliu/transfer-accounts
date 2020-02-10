package com.demo.transfer.adapter.api.dto;

import com.demo.transfer.domain.model.transfer.TransferStatus;
import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * description: 回调接口请求 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@ApiModel("转账回调Request")
public class TransferCallbackRequest {
    @ApiModelProperty(value = "交易流水号", name = "orderSeq")
    private String orderSeq;
    @ApiModelProperty(value = "交易状态", name = "status")
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
