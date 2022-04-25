package com.lucas.deliveryapi.domain.exception;

public class PaymentMethodNotFoundException extends EntityNotFoundException{

    public PaymentMethodNotFoundException(String msg){
        super(msg);
    }

    public PaymentMethodNotFoundException(Long id){
        this("Forma de pagamento não encontrada, id:" + id);
    }



}
