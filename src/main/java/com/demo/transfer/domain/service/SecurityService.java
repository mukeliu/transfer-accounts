package com.demo.transfer.domain.service;

import org.springframework.stereotype.Service;

/**
 * description: FailedReceiptTransferCompensation <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Service
public class SecurityService {

    public boolean securitySCheck(String secret) {
        return true;
    }

}
