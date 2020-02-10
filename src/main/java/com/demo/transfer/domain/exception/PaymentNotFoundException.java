package com.demo.transfer.domain.exception;

/**
 * description: PaymentNotFoundException <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class PaymentNotFoundException extends TransferException {

    public PaymentNotFoundException(String orderSeq) {
        super(String.format("支付记录[%s]不存在", orderSeq));
    }
}
