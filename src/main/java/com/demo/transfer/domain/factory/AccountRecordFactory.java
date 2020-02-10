package com.demo.transfer.domain.factory;

import com.demo.transfer.domain.model.account.AccountRecord;
import com.demo.transfer.domain.model.account.AccountTransferType;
import com.demo.transfer.domain.model.transfer.TransferRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.demo.transfer.domain.model.transfer.TransferStatus.BEGIN;

/**
 * description: 账户收支记录工厂类，用处创建账户交易记录 <br>
 * date: 2020/2/8 1:33 <br>
 * author: kehong <br>
 * version: 1.0 <br>
 */
public class AccountRecordFactory {
    public static AccountRecord create(TransferRecord transferRecord, AccountTransferType type) {
        AccountRecord accountRecord = new AccountRecord();
        accountRecord.setOrderSeq(transferRecord.getOrderSeq());
        accountRecord.setPayerAccountNumber(transferRecord.getPayerAccountNumber());
        accountRecord.setPayeeAccountNumber(transferRecord.getPayeeAccountNumber());
        accountRecord.setAmount(transferRecord.getAmount());
        accountRecord.setCreatedTime(LocalDateTime.now());
        accountRecord.setType(type);
        return accountRecord;
    }
}
