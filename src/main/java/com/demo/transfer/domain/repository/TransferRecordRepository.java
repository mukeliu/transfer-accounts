package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;

import java.util.List;


public interface TransferRecordRepository {

    /**
     * description: save transfer record <br>
     *
     * @param transferRecord transfer record
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    void save(TransferRecord transferRecord);

    /**
     * description: 更新转账记录状态 <br>
     * @param transferRecord： 转账记录
     * @param newStatus：新状态
     * @return: void
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    void updateTransferStatus(TransferRecord transferRecord, TransferStatus newStatus);

    TransferRecord findByOrderSeq(String orderSeq);

    /**
     * description: findByStatus <br>
      * @param status: 交易状态
     * @return: java.util.List<com.demo.transfer.domain.model.TransferRecord>
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    List<TransferRecord> findByStatus(TransferStatus status);
}
