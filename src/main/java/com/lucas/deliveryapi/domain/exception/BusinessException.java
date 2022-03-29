package com.lucas.deliveryapi.domain.exception;

//@ResponseStatus(value = HttpStatus.BAD_REQUEST)
public class BusinessException extends RuntimeException{
    public BusinessException(String msg){
        super(msg);
    }


    public BusinessException(String msg, Throwable cause){
        super(msg, cause);
    }

}
