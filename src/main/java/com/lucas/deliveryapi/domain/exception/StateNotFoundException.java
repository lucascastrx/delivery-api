package com.lucas.deliveryapi.domain.exception;

public class StateNotFoundException extends EntityNotFoundException{

    public StateNotFoundException(String msg){
        super(msg);
    }

    public StateNotFoundException(Long id){
        this("Estado não encontrado, id:" + id);
    }



}
