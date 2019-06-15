package com.example.fivefivemm.exception;

import com.example.fivefivemm.utility.Utility;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * 全局异常捕获类
 */
@ControllerAdvice
public class RestExceptionHandler {

    private Logger logger = LogManager.getLogger(this.getClass());

    @ExceptionHandler(value = NotFoundException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleNotFoundException(NotFoundException e) {
        logger.info("[当场抓获一个异常:异常信息:" + e.getMessage() + ",异常代码:" + e.getCode() + "]");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utility.ResultBody(e.getCode(), e.getMessage(), null));
    }

    @ExceptionHandler(value = BusinessException.class)
    @ResponseBody
//    @ResponseStatus(HttpStatus.NOT_FOUND)
    public ResponseEntity handleBusinessException(BusinessException e) {
        logger.info("[当场抓获一个异常:异常信息:" + e.getMessage() + ",异常代码:" + e.getCode() + "]");
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(Utility.ResultBody(e.getCode(), e.getMessage(), null));
    }
}
