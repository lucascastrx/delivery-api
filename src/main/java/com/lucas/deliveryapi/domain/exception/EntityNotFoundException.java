package com.lucas.deliveryapi.domain.exception;

//@ResponseStatus(value = HttpStatus.NOT_FOUND) //, reason = "Entidade não encontrada")
public abstract class EntityNotFoundException extends BusinessException {//extends ResponseStatusException {

//    public EntityNotFoundException(HttpStatus status, String reason) {
//        super(status, reason);
//    }
//
//    public EntityNotFoundException (String msg){
//        super(HttpStatus.valueOf(404),msg);
//    }

    public EntityNotFoundException (String msg){
        super(msg);
    }


}
