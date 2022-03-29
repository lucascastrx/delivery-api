package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.domain.model.Estado;
import com.lucas.deliveryapi.domain.repository.EstadoRepository;
import com.lucas.deliveryapi.domain.service.EstadoService;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@RestController
@RequestMapping("/estados")
public class EstadoController {

    @Autowired
    private EstadoRepository estadoRepository;

    @Autowired
    private
    EstadoService estadoService;

    @GetMapping
    public List<Estado> list(){
        return estadoRepository.findAll();
    }

    @GetMapping("/{estadoId}")
    public Estado findById(@PathVariable Long estadoId){
        return estadoService.findById(estadoId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Estado addEstado(@RequestBody @Valid Estado estado){
        return estadoService.addEstado(estado);
    }

    @PutMapping("/{estadoId}")
    public Estado update(@PathVariable Long estadoId, @RequestBody @Valid Estado estado) {
        var o = estadoService.findById(estadoId);
        BeanUtils.copyProperties(estado, o, "id");
        return estadoService.addEstado(o);
    }

    @DeleteMapping("/{estadoId}")
    public void delete(@PathVariable Long estadoId){
            estadoService.delete(estadoId);
    }
}
