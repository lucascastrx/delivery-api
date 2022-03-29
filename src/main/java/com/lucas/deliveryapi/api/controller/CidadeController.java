package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.StateNotFoundException;
import com.lucas.deliveryapi.domain.model.Cidade;
import com.lucas.deliveryapi.domain.repository.CidadeRepository;
import com.lucas.deliveryapi.domain.service.CidadeService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/cidades")
public class CidadeController {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private CidadeService cidadeService;

    @GetMapping
    @ResponseStatus(HttpStatus.OK)
    public List<Cidade> list() {
        return cidadeRepository.findAll();
    }

    @GetMapping("/{cidadeId}")
    public Cidade findById(@PathVariable Long cidadeId) {
        return cidadeService.findById(cidadeId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Cidade addCidade(@RequestBody @Valid Cidade cidade) {
        try {
            return cidadeService.addCidade(cidade);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @PutMapping("/{cidadeId}")
    public Cidade update(@PathVariable Long cidadeId,
                         @RequestBody @Valid Cidade cidade) {
        var o = cidadeService.findById(cidadeId);
        BeanUtils.copyProperties(cidade, o, "id");

        try {
            return cidadeService.addCidade(o);
        } catch (StateNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/{cidadeId}")
    public void delete(@PathVariable Long cidadeId) {
        cidadeService.delete(cidadeId);
    }

}