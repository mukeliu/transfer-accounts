package com.demo.transfer.adapter.api.dto;

/**
 * description: OrderSeqResponse <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class OrderSeqResponse {
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
