package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.model.Account;

import java.math.BigDecimal;

public interface AccountRepository {

    Account findByNumber(String accountNumber);

    boolean minusAmount(Account payerAccount, BigDecimal amount);

    boolean addBalance(Account payeeAccount, BigDecimal amount);
}
