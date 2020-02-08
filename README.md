## 题目

编写2个用户之间的转账接口及其内部实现？
   **要求**：完成接口设计、并实现其内部逻辑，以完成A用户转账给B用户的功能。2个用户的账户不在同一个数据库下。注意需要手写代码，尽量不要用伪代码。
   **提示**：接口发布后会暴露给外部应用进行服务调用，请考虑接口规范、安全、幂等、重试、并发、有可能的异常分支、事务一致性、用户投诉、资金安全等的处理。
    
## 思路

     1.安全：采用加密token保证接口安全；
     2.幂等、重试：使用转账记录表作为去重表，以转账流水号作为唯一约束，在扣款前，先将插入转账记录表，捕获重复约束异常。
     4.并发：使用乐观锁 + 事务， 对于账户加入一个 version 字段
          扣款：update table set amount = amount - #{amount},version_id =  #{versionId} + 1 where accountId = #{accountId} and version_id = #{versionId}
          存款：update table set amount = amount + #{amount},version_id =  #{versionId} + 1 where accountId = #{accountId} and version_id = #{versionId}
     5.异常分支：
        a.安全性校验不通过；
        b.参数校验不通过；
        c.账户号与转账人姓名不符
        d.账户状态不可用，如账户处于冻结、待销户等等
        e.风控管制，不可转账
        d.余额不足；
        f:扣款、付款失败。
     6.事务一致性：题中说明两个账户在两个不同的库中（如果类似跨行转账，还涉及扣款与付款还在不同应用中），这是一个典型的分布式事务问题，
     这里采用 MQ消息 的方式的将扣款与收款分为两个本地事务：
        扣款：
            1)  生成转账记录并入库，如果已经存在该转账记录，表示已扣款成功，直接返回
            2） 先同步发送 prepare 消息到队列（prepare 消息对消费者不可见或者消费者过滤该类型消息）并拿到消息的地址，如果发送失败，则直接返回转账失败；
            3） 拿到消息地址后，执行本地扣款事务（扣款+更新交易状态为成功），同样如果事务失败，将整体回滚
            4） 本地扣款事务执行成功，向 MQ 异步发送确认扣款信息，
            5）以上步骤都成功的话则直接返回用户转账成功。
         收款：
            1) 从 MQ 收到转账通知，类型为设置为 RECEIPT, 
            2）收款人金额增加，在收款方的库存入转账记录，并状态置为成功。如果失败，则多次重试（最大重试次数可以配置),则将交易状态置为 FAILED
         定时补偿：
            1） 针对对于扣款步骤 4 发送确认扣款消息丢失时，可以以定时任务拿到队列中所有 prepare 的消息，再查询扣款状态，如果扣款成功，则将消息置为 DONE；
            2） 当重试收款失败次数超过设置的最大次数时，可以通过定时任务扫描所有收款类型（RECEIPT）的转账记录，再向扣款账户发起反向冲正交易；

     7.用户投诉：针对不同的异常分支，可以用枚举或者错误码记录异常类型，用于反馈，记录好关键交易日志，以供取证与追溯
     8.资金安全：先扣款，再付款，保证资金安全；
     
