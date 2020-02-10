package com.demo.transfer.domain.service;

import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.factory.PaymentFactory;
import com.demo.transfer.domain.model.account.Account;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.repository.AccountRepository;
import com.demo.transfer.domain.repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DuplicateKeyException;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.util.Objects;

/**
 * description: 支付服务 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Service
public class PayService {

    private final AccountRepository accountRepository;
    private final PaymentRepository paymentRepository;
    private final AccountService accountService;

    @Autowired
    public PayService(AccountRepository accountRepository,
                      PaymentRepository paymentRepository,
                      AccountService accountService) {
        this.accountRepository = accountRepository;
        this.paymentRepository = paymentRepository;
        this.accountService = accountService;
    }

    /**
     * description: 支付操作 <br>
     *
     * @param transferRecord：交易记录
     * @return: boolean
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public boolean pay(TransferRecord transferRecord) {
        // 插入去重表去重
        try {
            paymentRepository.save(PaymentFactory.create(transferRecord));
        } catch (DuplicateKeyException e) {
            // 已存在payment，表明已经发起过支付，直接返回true
            return true;
        }

        Account payerAccount = accountRepository.findByNumber(transferRecord.getPayerAccountNumber());
        Account payeeAccount = accountRepository.findByNumber(transferRecord.getPayeeAccountNumber());

        // 账户预检查，包括收/扣款账户姓名、状态、余额等等
        preCheck(payerAccount, payeeAccount, transferRecord);
        // 发起扣款流程
        return accountService.minusBalance(payerAccount, transferRecord.getOrderSeq());
    }

    private void preCheck(Account payerAccount, Account payeeAccount, TransferRecord payment) {
        if (!payerAccount.isNormal()) {
            throw new TransferException(String.format("账户状态[%s]不支持转账", payerAccount.getStatus()));
        }

        if (!payeeAccount.isNormal()) {
            throw new TransferException(String.format("账户状态[%s]不支持转账", payeeAccount.getStatus()));
        }

        if (!Objects.equals(payerAccount.getName(), payment.getPayerName())) {
            throw new TransferException("扣款人账户姓名不匹配");
        }

        if (!Objects.equals(payeeAccount.getName(), payment.getPayeeName())) {
            throw new TransferException("收款人账户姓名不匹配");
        }

        if (!payerAccount.isBalanceSufficient(payment.getAmount())) {
            throw new TransferException("余额不足");
        }
    }
}
