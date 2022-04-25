package com.lucas.deliveryapi.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException{

    public OrderNotFoundException(String msg){
        super(msg);
    }

    public OrderNotFoundException(Long id){
        this("Pedido não encontrado, id:" + id);
    }



}
