package com.demo.transfer.application;

import com.demo.transfer.domain.exception.TransferRecordNotFoundException;
import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import com.demo.transfer.domain.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;

@Service
public class TransferApplicationService {

    private final TransferService transferService;
    private final TransferRecordRepository transferRecordRepository;

    @Autowired
    public TransferApplicationService(TransferService transferService,
                                      TransferRecordRepository transferRecordRepository) {
        this.transferService = transferService;
        this.transferRecordRepository = transferRecordRepository;
    }

    @Transactional
    public TransferRecord beginTransfer(Transfer transfer) {
        return transferRecordRepository
            .selectByOrderSeqForUpdate(transfer.getOrderSeq())
            .orElseGet(() -> transferService.transfer(transfer));
    }

    public TransferStatus queryTransferStatus(String orderSeq) {
        return transferRecordRepository.findByOrderSeq(orderSeq)
            .map(TransferRecord::getStatus)
            .orElseThrow(() -> new TransferRecordNotFoundException(orderSeq));
    }

    @Transactional
    public TransferRecord callback(String orderSeq, TransferStatus status) {
        TransferRecord transferRecord = transferRecordRepository.findByOrderSeq(orderSeq)
            .orElseThrow(() -> new TransferRecordNotFoundException(orderSeq));
        transferRecord.setEndTime(LocalDateTime.now());
        transferRecordRepository.updateTransferStatus(transferRecord, status);
        if (status == TransferStatus.FAILED) {
            transferService.reverse(transferRecord);
        }
        return transferRecord;
    }
}
