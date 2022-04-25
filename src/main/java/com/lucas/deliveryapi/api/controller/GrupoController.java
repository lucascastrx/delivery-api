package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.grupo.input.GrupoInputDTO;
import com.lucas.deliveryapi.api.model.grupo.output.GrupoDTO;
import com.lucas.deliveryapi.domain.model.Grupo;
import com.lucas.deliveryapi.domain.repository.GrupoRepository;
import com.lucas.deliveryapi.domain.service.GrupoService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/grupos")
public class GrupoController {

    private GrupoRepository grupoRepository;

    private GrupoService grupoService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<GrupoDTO> list(){
        return mapperDTO.toCollection(grupoRepository.findAll(), GrupoDTO.class);
    }

    @GetMapping("/{grupoId}")
    public GrupoDTO findById(@PathVariable Long grupoId){
        return mapperDTO.transform(grupoService.findById(grupoId), GrupoDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public GrupoDTO addGrupo(@RequestBody @Valid GrupoInputDTO grupoInputDTO){
        var grupo = mapperDTO.transform(grupoInputDTO, Grupo.class);
        grupo = grupoService.addGrupo(grupo);
        return mapperDTO.transform(grupo, GrupoDTO.class);
    }

    @PutMapping("/{grupoId}")
    public GrupoDTO update(@PathVariable Long grupoId, @RequestBody @Valid GrupoInputDTO grupoInputDTO){
        var grupo = grupoService.findById(grupoId);
        mapperDTO.copyToDomain(grupoInputDTO,grupo);
        grupo = grupoService.addGrupo(grupo);
        return mapperDTO.transform(grupo,GrupoDTO.class);
    }


    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void delete(@PathVariable Long grupoId){
        grupoService.delete(grupoId);
    }
}
