package com.demo.transfer.application;

import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class TransferApplicationService {

    private final TransferService transferService;

    @Autowired
    public TransferApplicationService(TransferService transferService) {
        this.transferService = transferService;
    }

    public Transfer transfer(Transfer transfer) {
        return transferService.doTransfer(transfer);
    }

}
