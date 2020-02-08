package com.demo.transfer.application;

import com.demo.transfer.domain.exception.TransferRecordNotFoundException;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import com.demo.transfer.domain.service.NotifyService;
import com.demo.transfer.domain.service.TransferService;
import org.springframework.stereotype.Service;

/**
 * description: TransferEventHandler <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Service
public class TransferEventHandler {
    private final TransferService transferService;
    private final NotifyService notifyService;
    private final TransferRecordRepository transferRecordRepository;

    public TransferEventHandler(TransferService transferService,
                                NotifyService notifyService,
                                TransferRecordRepository transferRecordRepository) {
        this.transferService = transferService;
        this.notifyService = notifyService;
        this.transferRecordRepository = transferRecordRepository;
    }

    public boolean receipt(String orderSeq) {
        TransferRecord transferRecord = transferRecordRepository.findByOrderSeq(orderSeq)
            .orElseThrow(() -> new TransferRecordNotFoundException(orderSeq));
        boolean success = transferService.receipt(transferRecord);
        if (success) {
            notifyService.notifyAsync(transferRecord);
        }
        return success;
    }
}
