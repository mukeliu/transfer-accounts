package com.demo.transfer.domain.model.account;

import java.math.BigDecimal;
import java.time.LocalDateTime;

/**
 * description: 账户对象 <br>
 * date: 2020/2/8  <br>
 * author: kehong <br>
 * version: 1.0 <br>
 */
public class Account {
    /**
     * 账户 id
     */
    private Long id;
    /**
     * 账户号
     */
    private String number;
    /**
     * 账户余额
     */
    private BigDecimal balance;
    /**
     * 冻结额度
     */
    private BigDecimal freezeAmount;
    /**
     * 账户人姓名
     */
    private String name;
    /**
     * 账户人电话号码
     */
    private String phoneNumber;
    /**
     * 账户状态
     */
    private AccountStatus status;
    /**
     * 创建时间
     */
    private LocalDateTime createdTime;
    /**
     * 更新时间
     */
    private LocalDateTime updatedTime;
    /**
     * 版本号
     */
    private long version;

    public boolean isBalanceSufficient(BigDecimal amount) {
        return balance.compareTo(amount) > 0;
    }

    public boolean isNormal() {
        return status == AccountStatus.NORMAL;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getNumber() {
        return number;
    }

    public void setNumber(String number) {
        this.number = number;
    }

    public BigDecimal getBalance() {
        return balance;
    }

    public void setBalance(BigDecimal balance) {
        this.balance = balance;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPhoneNumber() {
        return phoneNumber;
    }

    public void setPhoneNumber(String phoneNumber) {
        this.phoneNumber = phoneNumber;
    }

    public AccountStatus getStatus() {
        return status;
    }

    public void setStatus(AccountStatus status) {
        this.status = status;
    }

    public long getVersion() {
        return version;
    }

    public void setVersion(long version) {
        this.version = version;
    }

    public LocalDateTime getCreatedTime() {
        return createdTime;
    }

    public void setCreatedTime(LocalDateTime createdTime) {
        this.createdTime = createdTime;
    }

    public LocalDateTime getUpdatedTime() {
        return updatedTime;
    }

    public void setUpdatedTime(LocalDateTime updatedTime) {
        this.updatedTime = updatedTime;
    }

}
