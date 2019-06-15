package com.example.fivefivemm.exception;

/**
 * 资源不存在异常类
 */
public class NotFoundException extends GlobalException {

    public NotFoundException(String message, int code) {
        super(message, code);
    }
}
