package com.shinemo.bi.ms.system.advice;

import com.shinemo.bi.ms.system.common.config.MessageConfig;
import com.shinemo.common.result.SmResult;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import javax.servlet.http.HttpServletRequest;

/**
 * Create Time:2020-02-26
 * User: luchao
 * Email: luc@shinemo.com
 */
@RestControllerAdvice
@Slf4j
public class ExceptionHandlerAdvice {

    @ExceptionHandler(value = Exception.class)
    public SmResult globalExceptionHandler(HttpServletRequest request, Exception e) {
        String uri = request.getRequestURI();
        log.error("globalExceptionHandler - uri:{},msg:{},ex:", uri, e.getMessage(), e);
        return SmResult.fail(MessageConfig.SYSTEM_ERROR);
    }
}
