package com.demo.transfer.application;

import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import com.demo.transfer.domain.service.AccountService;
import com.demo.transfer.domain.service.NotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * description: 交易事件处理器 <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Service
public class TransferEventHandler {
    private final AccountService accountService;
    private final NotifyService notifyService;
    private final TransferRecordRepository transferRecordRepository;

    @Autowired
    public TransferEventHandler(AccountService accountService,
                                NotifyService notifyService,
                                TransferRecordRepository transferRecordRepository) {
        this.accountService = accountService;
        this.notifyService = notifyService;
        this.transferRecordRepository = transferRecordRepository;
    }

    /**
     * description: 处理收款流程 <br>
     *
     * @param orderSeq： 交易流水号
     * @return: boolean
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    public boolean receipt(String orderSeq) {
        TransferRecord transferRecord = transferRecordRepository.findByOrderSeq(orderSeq);
        try {
            boolean success = accountService.addBalance(transferRecord);
            if (success) {
                notifyService.notifyAsync(transferRecord);
            }
            return success;
        } catch (Exception e) {
            return false;
        }
    }
}
