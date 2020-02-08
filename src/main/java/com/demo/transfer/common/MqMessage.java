package com.demo.transfer.common;

/**
 * description: MqMessage <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class MqMessage<T> {
    private String address;
    private T payload;

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public T getPayload() {
        return payload;
    }

    public void setPayload(T payload) {
        this.payload = payload;
    }
}
