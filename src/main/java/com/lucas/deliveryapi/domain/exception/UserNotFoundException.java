package com.lucas.deliveryapi.domain.exception;

public class UserNotFoundException extends EntityNotFoundException{

    public UserNotFoundException(String msg){
        super(msg);
    }

    public UserNotFoundException(Long id){
        this("Usuário não encontrado, id:" + id);
    }



}
