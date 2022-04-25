package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.estado.input.EstadoInputDTO;
import com.lucas.deliveryapi.api.model.estado.output.EstadoDTO;
import com.lucas.deliveryapi.domain.model.Estado;
import com.lucas.deliveryapi.domain.repository.EstadoRepository;
import com.lucas.deliveryapi.domain.service.EstadoService;
import lombok.AllArgsConstructor;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/estados")
public class EstadoController {

    private EstadoRepository estadoRepository;

    private EstadoService estadoService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<EstadoDTO> list(){
        return mapperDTO.toCollection(estadoRepository.findAll(),EstadoDTO.class);
    }

    @GetMapping("/{estadoId}")
    public EstadoDTO findById(@PathVariable Long estadoId){
        return mapperDTO.transform(estadoService.findById(estadoId), EstadoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public EstadoDTO addEstado(@RequestBody @Valid EstadoInputDTO estadoInputDTO){
        var estado = mapperDTO.transform(estadoInputDTO, Estado.class);
        estado = estadoService.addEstado(estado);
        return mapperDTO.transform(estado, EstadoDTO.class);
    }

    @PutMapping("/{estadoId}")
    public EstadoDTO update(@PathVariable Long estadoId, @RequestBody @Valid EstadoInputDTO estadoInputDTO) {

        var estado = estadoService.findById(estadoId);
        mapperDTO.copyToDomain(estadoInputDTO, estado);
        estado = estadoService.addEstado(estado);
        return mapperDTO.transform(estado, EstadoDTO.class);
    }

    @DeleteMapping("/{estadoId}")
    public void delete(@PathVariable Long estadoId){
            estadoService.delete(estadoId);
    }
}
