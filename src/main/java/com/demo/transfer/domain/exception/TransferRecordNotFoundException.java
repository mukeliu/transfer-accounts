package com.demo.transfer.domain.exception;

/**
 * description: AccountNotFoundException <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class TransferRecordNotFoundException extends TransferException {

    public TransferRecordNotFoundException(String orderSeq) {
        super(String.format("交易[%s]不存在", orderSeq));
    }
}
