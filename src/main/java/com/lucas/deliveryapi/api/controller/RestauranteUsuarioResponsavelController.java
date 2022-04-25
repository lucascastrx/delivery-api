package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.usuario.output.UsuarioDTO;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes/{restauranteId}/responsaveis")
public class RestauranteUsuarioResponsavelController {

    private RestauranteService restauranteService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<UsuarioDTO> list(@PathVariable Long restauranteId){
        var restaurante = restauranteService.findById(restauranteId);
        return mapperDTO.toCollection(restaurante.getResponsaveis(), UsuarioDTO.class);
    }

    @DeleteMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociateResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        restauranteService.addOrRemoveResponsavel(restauranteId, responsavelId);
    }

    @PutMapping("/{responsavelId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associateResponsavel(@PathVariable Long restauranteId, @PathVariable Long responsavelId){
        restauranteService.addOrRemoveResponsavel(restauranteId, responsavelId);
    }
}
