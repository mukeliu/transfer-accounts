package com.demo.transfer.domain.model.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * description: 账户交易记录 <br>
 * date: 2020/2/9 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class AccountRecord {

    private Long id;
    private String orderSeq;
    private String payerAccountNumber;
    private String payeeAccountNumber;
    private BigDecimal amount;
    private AccountTransferType type;
    private LocalDateTime createdTime;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getOrderSeq() {
        return orderSeq;
    }

    public void setOrderSeq(String orderSeq) {
        this.orderSeq = orderSeq;
    }

    public String getPayerAccountNumber() {
        return payerAccountNumber;
    }

    public void setPayerAccountNumber(String payerAccountNumber) {
        this.payerAccountNumber = payerAccountNumber;
    }

    public String getPayeeAccountNumber() {
        return payeeAccountNumber;
    }

    public void setPayeeAccountNumber(String payeeAccountNumber) {
        this.payeeAccountNumber = payeeAccountNumber;
    }

    public BigDecimal getAmount() {
        return amount;
    }

    public void setAmount(BigDecimal amount) {
        this.amount = amount;
    }

    public AccountTransferType getType() {
        return type;
    }

    public void setType(AccountTransferType type) {
        this.type = type;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }
}
