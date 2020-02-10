package com.demo.transfer.common;

import org.springframework.stereotype.Component;

import java.util.Collections;
import java.util.List;

/**
 * description: MQ操作模板类 <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Component
public class MqTemplate {

    /**
     * description: syncSend 同步发送，发送成功将返回消息的地址，失败返回 null<br>
     *
     * @param message： 消息体
     * @return: java.lang.String
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    public String syncSend(Object message) {
        return null;
    }

    /**
     * description: 异步发送 <br>
     *
     * @param message: 消息体
     * @return: boolean
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    public boolean asyncSend(Object message) {
        return true;
    }

    /**
     * description: 覆写指定地址的消息，异步执行 <br>
     *
     * @param address：消息地址
     * @param message      消息体
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    public void overrideMessage(String address, Object message) {

    }

    /**
     * description: 通过一定条件获取MQ中消息，返回消息集合。
     * 这里主要用来查询 MQ 中 PREPARE 状态的消息，用来作转账
     * 未通知到收款账户的补偿
     *
     * @param parameters： 查询条件
     * @return: java.util.List<java.lang.Object>
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    public <T> List<MqMessage<T>> retrieveMqMessages(Class<T> clazz, Object... parameters) {
        return Collections.emptyList();
    }

    /**
     * description: 删除队列指定的消息 <br>
      * @param address：消息地址
     * @return: void
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    public void deleteMessage(String address) {
    }
}
