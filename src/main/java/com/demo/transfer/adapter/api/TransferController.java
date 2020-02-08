package com.demo.transfer.adapter.api;

import com.demo.transfer.adapter.api.dto.TransferRequest;
import com.demo.transfer.adapter.api.dto.TransferResponse;
import com.demo.transfer.application.TransferApplicationService;
import com.demo.transfer.common.Result;
import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.model.Transfer;
import com.demo.transfer.domain.service.SecurityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.validation.Valid;

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
        Transfer transfer = transferApplicationService.transfer(transferRequest.toTransfer());
        return Result.success(TransferResponse.from(transfer));
    }
}
