package com.demo.transfer.adapter.api;

import com.demo.transfer.adapter.api.dto.*;
import com.demo.transfer.application.TransferApplicationService;
import com.demo.transfer.common.Result;
import com.demo.transfer.domain.exception.TransferException;
import com.demo.transfer.domain.model.transfer.TransferRecord;
import com.demo.transfer.domain.model.transfer.TransferStatus;
import com.demo.transfer.domain.service.SecurityService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.UUID;

@RestController
@RequestMapping("/transfers")
@Api(value = "转账controller", tags = {"转账操作接口"})
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
    @ApiOperation("发起转账")
    public Result<TransferResponse> transfer(@Valid @RequestBody @ApiParam TransferRequest transferRequest) {
        // 安全验证，可以用拦截器实现
        if (!securityService.securitySCheck(transferRequest.getSecret())) {
            throw new TransferException("请求非法");
        }
        TransferRecord transferRecord = transferApplicationService.transfer(transferRequest.toTransfer());
        return Result.success(TransferResponse.from(transferRecord));
    }

    @GetMapping("/{orderSeq}/status")
    @ApiOperation("查询交易状态")
    @ApiImplicitParam(name = "orderSeq", value = "交易流水号")
    public Result<TransferStatusResponse> queryTransferStatus(@PathVariable(value = "orderSeq") String orderSeq) {
        TransferStatus transferStatus = transferApplicationService.queryTransferStatus(orderSeq);
        return Result.success(new TransferStatusResponse(orderSeq, transferStatus));
    }

    @GetMapping("/order-seq")
    @ApiOperation("获取交易流水号")
    public Result<OrderSeqResponse> getOrderSeq() {
        return Result.success(new OrderSeqResponse(UUID.randomUUID().toString()));
    }

    @PostMapping("/callback")
    public Result<TransferResponse> callback(@Valid @RequestBody @ApiParam TransferCallbackRequest callbackRequest) {
        TransferRecord transferRecord = transferApplicationService.callback(
            callbackRequest.getOrderSeq(), callbackRequest.getStatus());
        return Result.success(TransferResponse.from(transferRecord));
    }
}
