package com.demo.transfer.common;

/**
 * description: 全局异常 <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
public class GlobalException extends RuntimeException {
    public GlobalException(String message) {
        super(message);
    }
}
