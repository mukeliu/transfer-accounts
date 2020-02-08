package com.demo.transfer.scheduler;

import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import com.demo.transfer.domain.service.TransferService;
import org.springframework.stereotype.Component;

import static com.demo.transfer.domain.model.TransferStatus.FAILED;

/**
 * description: FailedReceiptTransferCompensation <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Component
public class FailedReceiptTransferCompensationScheduler {
    private final TransferRecordRepository transferRecordRepository;
    private final TransferService transferService;

    public FailedReceiptTransferCompensationScheduler(TransferRecordRepository transferRecordRepository,
                                                      TransferService transferService) {
        this.transferRecordRepository = transferRecordRepository;
        this.transferService = transferService;
    }

    public void execute() {
        transferRecordRepository.findByStatus(TransferStatus.RECEIVING).stream()
            .peek(transferRecord -> {
                TransferStatus transferStatus = transferRecordRepository.queryReceiptStatus(transferRecord.getOrderSeq());
                transferRecord.setStatus(transferStatus);
            })
            .filter(transferRecord -> transferRecord.getStatus() == FAILED)
            .forEach(transferService::reverse);
    }
}
