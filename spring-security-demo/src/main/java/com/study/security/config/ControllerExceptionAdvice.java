package com.study.security.config;

import org.springframework.security.access.AccessDeniedException;
import org.springframework.stereotype.Component;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RestControllerAdvice;

/**
 * @author HLH
 * @version 1.0
 * @email 17703595860@163.com
 */
@ControllerAdvice
public class ControllerExceptionAdvice {

    //只有出现AccessDeniedException异常才调转403.jsp页面
    @ExceptionHandler(AccessDeniedException.class)
    public String handleAccessDeniedException(){
        return "forward:/403.jsp";
    }

}
