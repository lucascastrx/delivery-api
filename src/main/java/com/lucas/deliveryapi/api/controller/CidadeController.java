package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.cidade.input.CidadeInputDTO;
import com.lucas.deliveryapi.api.model.cidade.output.CidadeDTO;
import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.StateNotFoundException;
import com.lucas.deliveryapi.domain.model.Cidade;
import com.lucas.deliveryapi.domain.model.Estado;
import com.lucas.deliveryapi.domain.repository.CidadeRepository;
import com.lucas.deliveryapi.domain.service.CidadeService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/cidades")
public class CidadeController {

    private CidadeRepository cidadeRepository;

    private CidadeService cidadeService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<CidadeDTO> list() {
        return mapperDTO.toCollection(cidadeRepository.findAll(), CidadeDTO.class);
    }

    @GetMapping("/{cidadeId}")
    public CidadeDTO findById(@PathVariable Long cidadeId) {
        var cidade = cidadeService.findById(cidadeId);
        return mapperDTO.transform(cidade, CidadeDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public CidadeDTO addCidade(@RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        try {
            var cidade = mapperDTO.transform(cidadeInputDTO, Cidade.class);
            return mapperDTO.transform(cidadeService.addCidade(cidade), CidadeDTO.class);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public CidadeDTO update(@PathVariable Long cidadeId,
                         @RequestBody @Valid CidadeInputDTO cidadeInputDTO) {
        var cidade = cidadeService.findById(cidadeId);
        cidade.setEstado(new Estado());
        mapperDTO.copyToDomain(cidadeInputDTO, cidade);

        try {
            cidade = cidadeService.addCidade(cidade);
            return mapperDTO.transform(cidade, CidadeDTO.class);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    public void delete(@PathVariable Long cidadeId) {
        cidadeService.delete(cidadeId);
    }

}