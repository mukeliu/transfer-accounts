package com.demo.transfer.adapter.api;

import com.demo.transfer.adapter.api.dto.*;
import com.demo.transfer.application.TransferApplicationService;
import com.demo.transfer.common.Result;
import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.model.TransferRecord;
import com.demo.transfer.domain.model.TransferStatus;
import com.demo.transfer.domain.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/transfers")
public class TransferController {

    private final TransferApplicationService transferApplicationService;
    private final SecurityService securityService;

    @Autowired
    public TransferController(TransferApplicationService transferApplicationService,
                              SecurityService securityService) {
        this.transferApplicationService = transferApplicationService;
        this.securityService = securityService;
    }

    @PostMapping
    public Result<TransferResponse> transfer(@Valid @RequestBody TransferRequest transferRequest) {
        // 安全验证，可以用拦截器实现
        if (!securityService.securitySCheck(transferRequest.getSecret())) {
            throw new TransferException("请求非法");
        }
        TransferRecord transferRecord = transferApplicationService.beginTransfer(transferRequest.toTransfer());
        return Result.success(TransferResponse.from(transferRecord));
    }

    @GetMapping("/status")
    public Result<TransferStatusResponse> queryTransferStatus(@RequestParam String orderSeq) {
        TransferStatus transferStatus = transferApplicationService.queryTransferStatus(orderSeq);
        return Result.success(new TransferStatusResponse(orderSeq, transferStatus));
    }

    @GetMapping("/order-seq")
    public Result<OrderSeqResponse> getOrderSeq() {
        return Result.success(new OrderSeqResponse(UUID.randomUUID().toString()));
    }

    @PostMapping("/callback")
    public Result<TransferResponse> callback(@Valid @RequestBody TransferCallbackRequest callbackRequest) {
        TransferRecord transferRecord = transferApplicationService.callback(
            callbackRequest.getOrderSeq(), callbackRequest.getStatus());
        return Result.success(TransferResponse.from(transferRecord));
    }
}
