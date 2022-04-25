package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.permissao.output.PermissaoDTO;
import com.lucas.deliveryapi.domain.service.GrupoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/grupos/{grupoId}/permissoes")
public class GrupoPermissaoController {

    @Autowired
    private GrupoService grupoService;

    @Autowired
    private MapperDTO mapperDTO;

    @GetMapping
    public List<PermissaoDTO> list(@PathVariable Long grupoId){
        var grupo = grupoService.findById(grupoId);
        return mapperDTO.toCollection(grupo.getPermissoes(), PermissaoDTO.class);
    }

    @DeleteMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void disassociatePermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.addOrRemovePermission(grupoId, permissaoId);
    }

    @PutMapping("/{permissaoId}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void associatePermissao(@PathVariable Long grupoId, @PathVariable Long permissaoId){
        grupoService.addOrRemovePermission(grupoId, permissaoId);
    }
}
