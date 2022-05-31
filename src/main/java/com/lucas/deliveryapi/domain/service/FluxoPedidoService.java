package com.lucas.deliveryapi.domain.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class FluxoPedidoService {

    @Autowired
    private EmissaoPedidoService pedidoService;

    @Transactional
    public void confirmar(String id){
        var pedido = pedidoService.findByCodigo(id);
        pedido.confirmar();
    }

    @Transactional
    public void cancelar(String id){
        var pedido = pedidoService.findByCodigo(id);
        pedido.cancelar();

    }

    @Transactional
    public void entregar(String id){
        var pedido = pedidoService.findByCodigo(id);
        pedido.entregar();
    }


}
