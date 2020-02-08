package com.demo.transfer.domain.exception;

/**
 * description: AccountNotFoundException <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class AccountNotFoundException extends TransferException {

    public AccountNotFoundException(String accountNumber) {
        super(String.format("账户[%s]不存在", accountNumber));
    }

    public AccountNotFoundException(Object attachment, String accountNumber) {
        super(attachment, String.format("账户[%s]不存在", accountNumber));
    }
}
