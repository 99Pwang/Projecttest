package com.example.springboot.exception;

import com.example.springboot.common.Result;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

/**
 * Function：
 * Author：Peilongyu Wang
 * Date：2023/10/9 1:57
 */
@ControllerAdvice
public class GlobalException {
    @ExceptionHandler(ServiceException.class)
    @ResponseBody
    public Result serviceException(ServiceException exception){
        return Result.error(exception.getCode(),exception.getMessage() );
    }

    @ExceptionHandler(Exception.class)
    @ResponseBody
    public Result globalException(Exception e){
        return Result.error("500","System error" );
    }
}