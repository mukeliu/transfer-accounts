package com.demo.transfer.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class Result<T> {
    private boolean success;

    private T data;

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
