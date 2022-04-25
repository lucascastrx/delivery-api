package com.lucas.deliveryapi.domain.exception;

public class GroupNotFoundException extends EntityNotFoundException{

    public GroupNotFoundException(String msg){
        super(msg);
    }

    public GroupNotFoundException(Long id){
        this("Grupo não encontrado, id:" + id);
    }



}
