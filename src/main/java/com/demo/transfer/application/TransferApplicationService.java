package com.demo.transfer.application;

import com.demo.transfer.domain.model.transfer.Transfer;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.model.transfer.TransferStatus;
import com.demo.transfer.domain.repository.TransferRecordRepository;
import com.demo.transfer.domain.service.PayService;
import com.demo.transfer.domain.service.TransferService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import javax.transaction.Transactional;
import java.time.LocalDateTime;
import java.util.concurrent.CompletableFuture;

import static com.demo.transfer.domain.model.transfer.TransferStatus.BEGIN;

@Service
public class TransferApplicationService {

    private final TransferService transferService;
    private final PayService payService;
    private final TransferRecordRepository transferRecordRepository;

    @Autowired
    public TransferApplicationService(TransferService transferService,
                                      PayService payService,
                                      TransferRecordRepository transferRecordRepository) {
        this.transferService = transferService;
        this.payService = payService;
        this.transferRecordRepository = transferRecordRepository;
    }

    /**
     * description: 转账操作 <br>
     *
     * @param transfer：转账信息
     * @return: com.demo.transfer.domain.model.transfer.TransferRecord
     * date: 2020/2/10 <br>
     * version: 1.0 <br>
     */
    public TransferRecord transfer(Transfer transfer) {
        // 创建转账任务
        TransferRecord transferRecord = transferService.findExistedOrCreateTransferRecord(transfer);
        // 异步调起支付服务
        CompletableFuture.supplyAsync(() -> payService.pay(transferRecord))
            // 回调更新交易状态
            .whenComplete((paySuccess, throwable) -> {
                    if (throwable == null) {
                        // 支付过程成功，状态更新为 DEDUCTED，否则转账失败，置为 FAILED
                        TransferStatus newStatus = paySuccess ? TransferStatus.DEDUCTED : TransferStatus.FAILED;
                        transferRecordRepository.updateTransferStatus(transferRecord, BEGIN, newStatus);
                    } else {
                        // 支付异常时，交易失败，置状态为 FAILED
                        transferService.fail(transferRecord, throwable.getMessage());
                    }
                }
            );
        return transferRecord;
    }

    public TransferStatus queryTransferStatus(String orderSeq) {
        return transferRecordRepository.findByOrderSeq(orderSeq).getStatus();
    }

    /**
     * description: 处理回调请求 <br>
     *
     * @param orderSeq：   交易流水号
     * @param status：交易状态
     * @return: com.demo.transfer.domain.model.transfer.TransferRecord
     * date: 2020/2/9 <br>
     * version: 1.0 <br>
     */
    @Transactional
    public TransferRecord callback(String orderSeq, TransferStatus status) {
        TransferRecord transferRecord = transferRecordRepository.findByOrderSeq(orderSeq);
        transferRecord.setEndTime(LocalDateTime.now());
        // 更新交易状态为回调中的状态
        transferRecordRepository.updateTransferStatus(transferRecord, transferRecord.getStatus(), status);
        // 如果收款失败，进行冲正操作
        if (status == TransferStatus.FAILED) {
            transferService.reverse(transferRecord);
        }
        return transferRecord;
    }
}
