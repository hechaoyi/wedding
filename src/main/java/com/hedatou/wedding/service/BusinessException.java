package com.hedatou.wedding.service;

public class BusinessException extends RuntimeException {

    private static final long serialVersionUID = 3157825568492825099L;

    public BusinessException(String message) {
        super(message);
    }

}
