package com.demo.transfer.adapter.db;

import com.demo.transfer.domain.model.account.Account;
import com.demo.transfer.domain.model.account.AccountRecord;
import com.demo.transfer.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * description: AccountRepositoryImpl <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Repository
public class AccountRepositoryImpl implements AccountRepository {
    @Override
    public Account findByNumber(String accountNumber) {
        return null;
    }

    @Override
    public boolean minusAmount(Account payerAccount, BigDecimal amount) {
        // update amount set amount = amount - #{amount},version_id =  #{versionId} + 1
        // where accountId = #{accountId} and version_id = #{versionId}
        // 比较更新行数，行数 > 0 则为 true
        return true;
    }

    @Override
    public boolean addBalance(Account payeeAccount, BigDecimal amount) {
        // update amount set amount = amount + #{amount},version_id =  #{versionId} + 1
        // where accountId = #{accountId} and version_id = #{versionId}
        // 比较更新行数，行数 > 0 则为 true
        return true;
    }

    @Override
    public void saveAccountRecord(AccountRecord accountRecord) {
        // insert
    }
}
