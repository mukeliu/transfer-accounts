package com.demo.transfer.common;

/**
 * description: MQ消息状态
 *      PREPARE - 准备状态，此状态下消息对消费者不可见
 *      DONE - 可用状态，只有此状态下的消息才能被消费
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public enum MqMessageStatus {
    PREPARE, DONE
}
