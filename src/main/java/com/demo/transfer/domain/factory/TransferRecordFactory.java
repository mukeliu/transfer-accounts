package com.demo.transfer.domain.factory;

import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.model.TransferRecord;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.UUID;

import static com.demo.transfer.domain.model.TransferStatus.BEGIN;

/**
 * description: TransferRecordFactory <br>
 * date: 2020/2/8 1:33 <br>
 * author: kehong <br>
 * version: 1.0 <br>
 */
public class TransferRecordFactory {
    public static TransferRecord create(Transfer transfer) {
        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setOrderSeq(transfer.getOrderSeq());
        transferRecord.setPayerAccountNumber(transfer.getPayerAccountNumber());
        transferRecord.setPayerName(transfer.getPayerName());
        transferRecord.setPayeeAccountNumber(transfer.getPayeeAccountNumber());
        transferRecord.setPayeeName(transfer.getPayeeName());
        transferRecord.setAmount(transfer.getAmount());
        transferRecord.setComment(transfer.getComment());
        transferRecord.setBeginTime(transfer.getBeginTime());
        transferRecord.setStatus(BEGIN);
        return transferRecord;
    }

    public static TransferRecord createReverseTransferRecord(String reverseAccountNumber,
                                                             String reverseAccountName,
                                                             BigDecimal amount) {
        TransferRecord transferRecord = new TransferRecord();
        transferRecord.setOrderSeq(UUID.randomUUID().toString());
        transferRecord.setPayerAccountNumber("bank-account");
        transferRecord.setPayerName("bank-name");
        transferRecord.setPayeeAccountNumber(reverseAccountNumber);
        transferRecord.setPayeeName(reverseAccountName);
        transferRecord.setAmount(amount);
        transferRecord.setComment("退款");
        transferRecord.setBeginTime(LocalDateTime.now());
        return transferRecord;
    }
}
