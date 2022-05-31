package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.pedido.input.PedidoInputDTO;
import com.lucas.deliveryapi.api.model.pedido.output.PedidoDTO;
import com.lucas.deliveryapi.api.model.pedido.output.PedidoResumoDTO;
import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.EntityNotFoundException;
import com.lucas.deliveryapi.domain.model.Pedido;
import com.lucas.deliveryapi.domain.model.Usuario;
import com.lucas.deliveryapi.domain.repository.PedidoRepository;
import com.lucas.deliveryapi.domain.service.EmissaoPedidoService;
import com.lucas.deliveryapi.domain.service.FluxoPedidoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/pedidos")
public class PedidoController {

    private PedidoRepository pedidoRepository;

    private EmissaoPedidoService emissaoPedidoService;

    private FluxoPedidoService fluxoPedidoService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<PedidoResumoDTO> list(){
        return mapperDTO.toCollection(pedidoRepository.findAll(), PedidoResumoDTO.class);
    }

    @GetMapping("/{pedidoId}")
    public PedidoDTO findById(@PathVariable String pedidoId){
        return mapperDTO.transform(emissaoPedidoService.findByCodigo(pedidoId), PedidoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public PedidoDTO addPedido(@RequestBody @Valid PedidoInputDTO pedidoInputDTO){
        try {
            var pedido = mapperDTO.transform(pedidoInputDTO, Pedido.class);
            pedido.setCliente(new Usuario());
            pedido.getCliente().setId(1L);

            pedido = emissaoPedidoService.emitir(pedido);

            return mapperDTO.transform(pedido, PedidoDTO.class);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{pedidoId}/confirmacao")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void confirmar(@PathVariable String pedidoId){
        fluxoPedidoService.confirmar(pedidoId);
    }

    @PutMapping("/{pedidoId}/cancelamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void cancelar(@PathVariable String pedidoId){
        fluxoPedidoService.cancelar(pedidoId);
    }

    @PutMapping("/{pedidoId}/entrega")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void entrega(@PathVariable String pedidoId){
        fluxoPedidoService.entregar(pedidoId);
    }

}
