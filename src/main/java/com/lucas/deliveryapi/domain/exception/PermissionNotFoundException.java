package com.lucas.deliveryapi.domain.exception;

public class PermissionNotFoundException extends EntityNotFoundException{

    public PermissionNotFoundException(String msg){
        super(msg);
    }

    public PermissionNotFoundException(Long id){
        this("Permissão não encontrada, id:" + id);
    }



}
