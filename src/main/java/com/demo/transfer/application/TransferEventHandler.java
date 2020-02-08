package com.demo.transfer.application;

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

    public TransferEventHandler(TransferService transferService,
                                NotifyService notifyService) {
        this.transferService = transferService;
        this.notifyService = notifyService;
    }

    public boolean receipt(TransferRecord transferRecord) {
        boolean success = transferService.receipt(transferRecord);
        if (success) {
            notifyService.notifyAsync(transferRecord);
        }
        return success;
    }
}
