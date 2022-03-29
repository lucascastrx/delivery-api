package com.lucas.deliveryapi.domain.exception;

public class RestaurantNotFoundException extends EntityNotFoundException{

    public RestaurantNotFoundException(String msg){
        super(msg);
    }

    public RestaurantNotFoundException(Long id){
        this("Restaurante não encontrado, id:" + id);
    }



}
