package com.demo.transfer.adapter.db;

import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

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
    public boolean updateTransferStatus(TransferRecord transferRecord, TransferStatus newStatus) {
        return true;
    }

    @Override
    public Optional<TransferRecord> findByOrderSeq(String orderSeq) {
        return null;
    }

    @Override
    public List<TransferRecord> findByStatus(TransferStatus status) {
        return null;
    }

    @Override
    public Optional<TransferRecord> selectByOrderSeqForUpdate(String orderSeq) {
        return Optional.empty();
    }
}
