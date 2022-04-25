package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.formapagamento.output.FormaPagamentoDTO;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/formas-pagamento")
public class RestauranteFormaPagamentoController {

    private RestauranteService restauranteService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<FormaPagamentoDTO> list(@PathVariable Long restauranteId){
        var restaurante = restauranteService.findById(restauranteId);
        return mapperDTO.toCollection(restaurante.getFormasPagamento(), FormaPagamentoDTO.class);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociateFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.addOrRemovePaymentMethod(restauranteId, formaPagamentoId);
    }

    @PutMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associateFormaPagamento(@PathVariable Long restauranteId, @PathVariable Long formaPagamentoId){
        restauranteService.addOrRemovePaymentMethod(restauranteId, formaPagamentoId);
    }
}
