package com.demo.transfer.domain.exception;

/**
 * description: AccountNotFoundException <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class AccountRecordNotFoundException extends TransferException {

    public AccountRecordNotFoundException(String accountNumber, String orderSeq) {
        super(String.format("账户[%s]的交易记录[%s]不存在", accountNumber, orderSeq));
    }
}
