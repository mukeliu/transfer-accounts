package com.demo.transfer.domain.model.transfer;

/**
 * description: 交易状态
 *      BEGIN - 开始转账
 *      DEDUCTING - 扣款中
 *      DEDUCTED - 已扣款
 *      RECEIVING - 收款中
 *      SUCCEED - 成功
 *      FAILED - 失败
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public enum TransferStatus {
    BEGIN, DEDUCTED, RECEIVING, SUCCEED, FAILED
}
