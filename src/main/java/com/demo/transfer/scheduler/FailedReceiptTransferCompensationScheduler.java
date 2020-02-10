package com.demo.transfer.scheduler;

import com.demo.transfer.domain.model.transfer.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import com.demo.transfer.domain.service.TransferService;
import org.springframework.stereotype.Component;

import static com.demo.transfer.domain.model.transfer.TransferStatus.FAILED;

/**
 * description: 补偿扣款成功，但收款永远失败的情况 <br>
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
        // 获取所有处于 RECEIVING
        transferRecordRepository.findByStatus(TransferStatus.RECEIVING).stream()
            .peek(transferRecord -> {
                TransferStatus transferStatus = transferRecordRepository.queryReceiptStatus(transferRecord.getOrderSeq());
                transferRecord.setStatus(transferStatus);
            })
            .filter(transferRecord -> transferRecord.getStatus() == FAILED)
            .forEach(transferService::reverse);
    }
}
