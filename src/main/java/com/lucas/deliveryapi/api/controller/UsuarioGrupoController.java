package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.grupo.output.GrupoDTO;
import com.lucas.deliveryapi.domain.service.UsuarioService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/usuarios/{usuarioId}/grupos")
public class UsuarioGrupoController {

    @Autowired
    private UsuarioService usuarioService;

    @Autowired
    private MapperDTO mapperDTO;

    @GetMapping
    public List<GrupoDTO> list(@PathVariable Long usuarioId){
        var usuario = usuarioService.findById(usuarioId);
        return mapperDTO.toCollection(usuario.getGrupos(), GrupoDTO.class);
    }

    @DeleteMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociateGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.addOrRemoveGrupo(usuarioId, grupoId);
    }

    @PutMapping("/{grupoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associateGrupo(@PathVariable Long usuarioId, @PathVariable Long grupoId){
        usuarioService.addOrRemoveGrupo(usuarioId, grupoId);
    }
}
