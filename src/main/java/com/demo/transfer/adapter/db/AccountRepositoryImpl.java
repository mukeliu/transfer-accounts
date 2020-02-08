package com.demo.transfer.adapter.db;

import com.demo.transfer.domain.model.Account;
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
        return true;
    }

    @Override
    public boolean addBalance(Account payeeAccount, BigDecimal amount) {
        return true;
    }
}
