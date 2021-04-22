package com.loctek.workflow.handler;

import com.loctek.workflow.entity.Resp;
import org.apache.ibatis.binding.BindingException;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@RestControllerAdvice
public class RestExceptionHandler {
    @ExceptionHandler({BindingException.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Resp<?> bindingExHandler(Exception e){
        e.printStackTrace();
        return Resp.fail("参数有误",e.getMessage());
    }

    @ExceptionHandler({Exception.class})
    @ResponseStatus(HttpStatus.INTERNAL_SERVER_ERROR)
    protected Resp<?> exHandler(Exception e){
        e.printStackTrace();
        return Resp.fail("服务器出错",e.getMessage());
    }
}
