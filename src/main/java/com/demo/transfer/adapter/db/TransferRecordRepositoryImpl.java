package com.demo.transfer.adapter.db;

import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.model.transfer.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

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
    public boolean updateTransferStatus(TransferRecord transferRecord, TransferStatus oldStatus, TransferStatus newStatus) {
        String orderSeq = transferRecord.getOrderSeq();

        // update transfer_record set ..., status = new status where  orderSeq = #{orderSeq} and status = #{oldStatus};
        // 更新行数
        int updatedLines = 0;
        return updatedLines > 0;
    }

    @Override
    public TransferRecord findByOrderSeq(String orderSeq) {
        return null;
    }

    @Override
    public List<TransferRecord> findByStatus(TransferStatus status) {
        return null;
    }

    @Override
    public TransferStatus queryReceiptStatus(String orderSeq) {
        return null;
    }

}
