package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.pedido.output.PedidoDTO;
import com.lucas.deliveryapi.api.model.pedido.output.PedidoResumoDTO;
import com.lucas.deliveryapi.domain.repository.PedidoRepository;
import com.lucas.deliveryapi.domain.service.EmissaoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoRepository pedidoRepository;

    private EmissaoPedidoService emissaoPedidoService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<PedidoResumoDTO> list(){
        return mapperDTO.toCollection(pedidoRepository.findAll(), PedidoResumoDTO.class);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO findById(@PathVariable Long pedidoId){
        return mapperDTO.transform(emissaoPedidoService.findById(pedidoId), PedidoDTO.class);
    }

}
