package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.exception.AccountNotFoundException;
import com.demo.transfer.domain.model.account.Account;
import com.demo.transfer.domain.model.account.AccountRecord;

import java.math.BigDecimal;

public interface AccountRepository {

    /**
     * description: 通过账户号查询账户 <br>
     *
     * @param accountNumber： 账户号
     * @return: com.demo.transfer.domain.model.account.Account
     * @throws: 账户未找到时，抛 AccountNotFoundException 异常
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    Account findByNumber(String accountNumber) throws AccountNotFoundException;


    /**
     * description: 扣除账户余额 <br>
     *
     * @param payerAccount： 扣款账户
     * @param amount：       扣款金额
     * @return: boolean
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    boolean minusAmount(Account payerAccount, BigDecimal amount);

    /**
     * description: 增加账户余额 <br>
     *
     * @param payeeAccount： 收款账户
     * @param amount： 收款金额
     * @return: boolean
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    boolean addBalance(Account payeeAccount, BigDecimal amount);

    /**
     * description: 保存账户记录 <br>
     *
     * @param accountRecord: 账户记录
     * @return: void
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    void saveAccountRecord(AccountRecord accountRecord);

}
