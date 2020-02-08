package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.model.Account;

import java.math.BigDecimal;
import java.util.Optional;

public interface AccountRepository {

    Account findByNumber(String accountNumber);

    void minusAmount(Account payerAccount, BigDecimal amount);

    void addBalance(Account payeeAccount, BigDecimal amount);
}
