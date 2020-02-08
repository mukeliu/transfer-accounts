package com.demo.transfer.adapter.db;

import com.demo.transfer.domain.model.Account;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.repository.AccountRepository;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.stereotype.Repository;

import java.math.BigDecimal;

/**
 * description: AccountRepositoryImpl <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Repository
public class TransferRecordRepositoryImpl implements TransferRecordRepository {

    @Override
    public void save(TransferRecord transferRecord) {

    }

    @Override
    public void updateTransferStatus(TransferRecord transferRecord, TransferStatus newStatus) {

    }
}
