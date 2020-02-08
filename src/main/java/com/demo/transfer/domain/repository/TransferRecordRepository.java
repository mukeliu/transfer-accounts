package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;

import java.util.List;
import java.util.Optional;


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
     *
     * @param transferRecord ： 转账记录
     * @param newStatus      ：新状态
     * @return: boolean
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    boolean updateTransferStatus(TransferRecord transferRecord, TransferStatus newStatus);

    /**
     * description: findByOrderSeq <br>
     *
     * @param orderSeq: 交易流水号
     * @return: java.util.Optional<com.demo.transfer.domain.model.TransferRecord>
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    Optional<TransferRecord> findByOrderSeq(String orderSeq);

    /**
     * description: findByStatus <br>
     *
     * @param status: 交易状态
     * @return: java.util.List<com.demo.transfer.domain.model.TransferRecord>
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    List<TransferRecord> findByStatus(TransferStatus status);

    Optional<TransferRecord> selectByOrderSeqForUpdate(String orderSeq);

    TransferStatus queryReceiptStatus(String order);
}
