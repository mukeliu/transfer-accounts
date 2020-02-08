package com.demo.transfer.adapter.db;

import com.demo.transfer.domain.model.Account;
import com.demo.transfer.domain.repository.AccountRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;
import java.util.Optional;

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
    public void minusAmount(Account payerAccount, BigDecimal amount) {

    }

    @Override
    public void addBalance(Account payeeAccount, BigDecimal amount) {

    }
}
