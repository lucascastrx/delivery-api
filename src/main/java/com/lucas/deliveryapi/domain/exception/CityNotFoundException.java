package com.lucas.deliveryapi.domain.exception;

public class CityNotFoundException extends EntityNotFoundException{

    public CityNotFoundException(String msg){
        super(msg);
    }

    public CityNotFoundException(Long id){
        this("Cidade não encontrada, id:" + id);
    }



}
