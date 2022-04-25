package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.OrderNotFoundException;
import com.lucas.deliveryapi.domain.model.Pedido;
import com.lucas.deliveryapi.domain.repository.PedidoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class EmissaoPedidoService {

    @Autowired
    private PedidoRepository pedidoRepository;

    public Pedido findById(Long id){
        return pedidoRepository.findById(id).orElseThrow(()->
                new OrderNotFoundException(id)
        );
    }
}
