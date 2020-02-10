package com.demo.transfer.domain.repository;

import com.demo.transfer.domain.exception.TransferRecordNotFoundException;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.model.transfer.TransferStatus;

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
     *
     * @param transferRecord ： 转账记录
     * @param oldStatus
     * @param newStatus      ：新状态
     * @return: boolean
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    boolean updateTransferStatus(TransferRecord transferRecord, TransferStatus oldStatus, TransferStatus newStatus);

    /**
     * description: findByOrderSeq <br>
     *
     * @param orderSeq: 交易流水号
     * @return: java.util.Optional<com.demo.transfer.domain.model.transfer.TransferRecord>
     * @throws: 当交易记录不存在时抛 TransferRecordNotFoundException
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    TransferRecord findByOrderSeq(String orderSeq) throws TransferRecordNotFoundException;

    /**
     * description: findByStatus <br>
     *
     * @param status: 交易状态
     * @return: java.util.List<com.demo.transfer.domain.model.transfer.TransferRecord>
     * date: 2020/2/8 <br>
     * version: 1.0 <br>
     */
    List<TransferRecord> findByStatus(TransferStatus status);

    /**
     * description: 查询收款是否成功 <br>
      * @param orderSeq: 交易流水号
     * @return: com.demo.transfer.domain.model.transfer.TransferStatus
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    TransferStatus queryReceiptStatus(String orderSeq);
}
