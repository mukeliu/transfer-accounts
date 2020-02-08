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

    protected MqMessageStatus mqMessageStatus;
    protected LocalDateTime createdTime;


    public BaseEvent(MqMessageStatus mqMessageStatus) {
        this.mqMessageStatus = mqMessageStatus;
    }
}
