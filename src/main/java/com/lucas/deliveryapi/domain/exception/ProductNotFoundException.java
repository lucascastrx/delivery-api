package com.lucas.deliveryapi.domain.exception;

public class ProductNotFoundException extends EntityNotFoundException{

    public ProductNotFoundException(String msg){
        super(msg);
    }

    public ProductNotFoundException(Long restauranteId, Long produtoId){
        this(String.format("Não existe um cadastro de produto com código %d para o restaurante de código %d",
                produtoId, restauranteId));
    }



}
