package com.lucas.deliveryapi.domain.exception;

//@ResponseStatus(HttpStatus.CONFLICT)
public class EntityUnviableException extends BusinessException {

    public EntityUnviableException(String msg){
        super(msg);
    }

}
