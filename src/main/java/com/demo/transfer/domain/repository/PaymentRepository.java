package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.exception.PaymentNotFoundException;
import com.demo.transfer.domain.model.payment.Payment;
import org.springframework.stereotype.Repository;

/**
 * description: PaymentRepository <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Repository
public interface PaymentRepository {

    /**
     * description: 通过流水号查询支付记录 <br>
     *
     * @param orderSeq： 交易流水号
     * @return: com.demo.transfer.domain.model.payment.Payment
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    Payment findByOrderSeq(String orderSeq) throws PaymentNotFoundException;

    /**
     * description: 保存支付记录 <br>
     *
     * @param payment： 支付记录
     * @return: Payment
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    Payment save(Payment payment);
}
