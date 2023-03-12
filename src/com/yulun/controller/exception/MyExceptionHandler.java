package com.yulun.controller.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import javax.servlet.http.HttpServletResponse;

@ControllerAdvice
public class MyExceptionHandler {
    @ExceptionHandler(value =Exception.class)
    public String exceptionHandler(HttpServletResponse response, Exception e){
        response.setHeader("Access-Control-Allow-Origin", "*");
        System.out.println("未知异常！原因是:"+e);
        return e.getMessage();
    }
}
