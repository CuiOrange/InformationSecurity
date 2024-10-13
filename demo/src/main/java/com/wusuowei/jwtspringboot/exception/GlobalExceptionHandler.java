package com.wusuowei.jwtspringboot.exception;

import com.wusuowei.jwtspringboot.exception.ServiceException;
import com.wusuowei.jwtspringboot.utils.R;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

@Slf4j
@RestControllerAdvice
public class GlobalExceptionHandler {



    @ExceptionHandler(value = RuntimeException.class)
    public com.wusuowei.jwtspringboot.utils.R handler(RuntimeException e){
        log.error("运行时异常：---------{}"+e.getMessage());
        return R.error(e.getMessage());
    }

    /**
     * 如果抛出的的是ServiceException，则调用该方法
     * @param se 业务异常
     * @return Result
     */
    @ExceptionHandler(ServiceException.class)
    public com.wusuowei.jwtspringboot.utils.R handle(ServiceException se){
        return R.error(se.getCode(), se.getMessage());
    }

}
