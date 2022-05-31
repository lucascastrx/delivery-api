package com.lucas.deliveryapi.domain.exception;

public class OrderNotFoundException extends EntityNotFoundException{



    public OrderNotFoundException(String id){
        super("Pedido não encontrado" +  id);
    }



}
