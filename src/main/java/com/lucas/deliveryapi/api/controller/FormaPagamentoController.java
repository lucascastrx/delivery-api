package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.formapagamento.input.FormaPagamentoInputDTO;
import com.lucas.deliveryapi.api.model.formapagamento.output.FormaPagamentoDTO;
import com.lucas.deliveryapi.domain.model.FormaPagamento;
import com.lucas.deliveryapi.domain.repository.FormaPagamentoRepository;
import com.lucas.deliveryapi.domain.service.FormaPagamentoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/formas-pagamento")
public class FormaPagamentoController {

    private FormaPagamentoRepository formaPagamentoRepository;

    private FormaPagamentoService formaPagamentoService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<FormaPagamentoDTO> list(){
        return mapperDTO.toCollection(formaPagamentoRepository.findAll(), FormaPagamentoDTO.class);
    }

    @GetMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO findById(@PathVariable Long formaPagamentoId){
        return mapperDTO.transform(formaPagamentoService.findById(formaPagamentoId), FormaPagamentoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public FormaPagamentoDTO addFormaPagamento(@RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO){
        var formaPagamento = mapperDTO.transform(formaPagamentoInputDTO, FormaPagamento.class);
        formaPagamento = formaPagamentoService.addFormaPagamento(formaPagamento);
        return mapperDTO.transform(formaPagamento, FormaPagamentoDTO.class);
    }

    @PutMapping("/{formaPagamentoId}")
    public FormaPagamentoDTO update(@PathVariable Long formaPagamentoId, @RequestBody @Valid FormaPagamentoInputDTO formaPagamentoInputDTO){
        var formaPagamento = formaPagamentoService.findById(formaPagamentoId);
        mapperDTO.copyToDomain(formaPagamentoInputDTO, formaPagamento);
        formaPagamento = formaPagamentoService.addFormaPagamento(formaPagamento);
        return mapperDTO.transform(formaPagamento, FormaPagamentoDTO.class);
    }

    @DeleteMapping("/{formaPagamentoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long formaPagamentoId){
        formaPagamentoService.delete(formaPagamentoId);
    }

}
