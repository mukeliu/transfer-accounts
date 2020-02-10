package com.demo.transfer.adapter.api.dto;

import io.swagger.annotations.ApiModel;
import io.swagger.annotations.ApiModelProperty;

/**
 * description: 请求获取交易流水号的响应类 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@ApiModel("交易流水号response")
public class OrderSeqResponse {
    @ApiModelProperty(value = "交易流水号", name = "orderSeq")
    private String orderSeq;

    public OrderSeqResponse(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }
}
