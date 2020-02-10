package com.demo.transfer.common;

import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * description: BaseEvent <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public abstract class BaseEvent implements Serializable {

    /**
     * MQ消息状态
     */
    protected MqMessageStatus mqMessageStatus;

    /**
     * 创建时间
     */
    protected LocalDateTime createdTime;

    public BaseEvent(MqMessageStatus mqMessageStatus) {
        this.mqMessageStatus = mqMessageStatus;
        this.createdTime = LocalDateTime.now();
    }

    public MqMessageStatus getMqMessageStatus() {
        return mqMessageStatus;
    }

    public void setMqMessageStatus(MqMessageStatus mqMessageStatus) {
        this.mqMessageStatus = mqMessageStatus;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
