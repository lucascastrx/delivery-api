package com.lucas.deliveryapi.domain.exception;

public class KitchenNotFoundException extends EntityNotFoundException{

    public KitchenNotFoundException(String msg){
        super(msg);
    }

    public KitchenNotFoundException(Long id){
        this("Cozinha não encontrada, id:" + id);
    }



}
