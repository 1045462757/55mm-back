package com.example.fivefivemm.exception;

/**
 * 业务异常类
 */
public class BusinessException extends GlobalException {

    public BusinessException(String message, int code) {
        super(message, code);
    }
}
