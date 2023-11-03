package com.example.springboot.exception;

import lombok.Getter;

@Getter

/**
 * Function：
 * Author：Peilongyu Wang
 * Date：2023/10/9 2:00
 */
public class ServiceException extends RuntimeException {
    private final String code;
    public ServiceException(String msg) {
        super(msg);
        this.code="500";
    }

    public ServiceException(String code,String msg) {
        super(msg);
        this.code=code;
    }
}