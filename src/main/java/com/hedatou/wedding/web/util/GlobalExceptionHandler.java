package com.hedatou.wedding.web.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.messaging.handler.annotation.MessageExceptionHandler;
import org.springframework.messaging.simp.annotation.SendToUser;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import com.hedatou.wedding.service.BusinessException;

@ControllerAdvice
public class GlobalExceptionHandler {

    private static final Logger logger = LoggerFactory.getLogger(GlobalExceptionHandler.class);

    @ExceptionHandler
    @ResponseBody
    public StdJson handle(Exception e) {
        logger.warn("unhandled exception", e);
        if (e instanceof BusinessException)
            return StdJson.err(e.getMessage());
        return StdJson.err("服务器出错啦~请稍后再试");
    }

    @MessageExceptionHandler
    @SendToUser("/queue/error")
    public StdJson handleWebSocketException(Exception e) {
        logger.warn("unhandled exception", e);
        if (e instanceof BusinessException)
            return StdJson.err(e.getMessage());
        return StdJson.err("服务器出错啦~请稍后再试");
    }

}
