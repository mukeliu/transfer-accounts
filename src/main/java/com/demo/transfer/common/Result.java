package com.demo.transfer.common;

import lombok.Data;
import lombok.NoArgsConstructor;

/**
 * description: api接口返回结果封装类 <br>
 * date: 2020/2/8 <br>
 * author: Kehong <br>
 * version: 1.0 <br>
 */
@Data
@NoArgsConstructor
public class Result<T> {
    /**
     * 请求处理是否成功
     */
    private boolean success;

    /**
     * 响应数据
     */
    private T data;

    /**
     * 异常信息
     */
    private String errorMessage;


    public Result(boolean success, T result, String errorMessage) {
        this.success = true;
        this.data = result;
        this.errorMessage = errorMessage;
    }


    public static <T> Result<T> success(T result) {
        return new Result<>(true, result, "");
    }

    public static <T> Result<T> fail(String errorMessage) {
        return new Result<>(false, null, errorMessage);
    }
}
