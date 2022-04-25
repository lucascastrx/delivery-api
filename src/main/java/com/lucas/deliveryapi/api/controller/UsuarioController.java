package com.lucas.deliveryapi.api.controller;

import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.model.usuario.input.SenhaInputDTO;
import com.lucas.deliveryapi.api.model.usuario.input.UsuarioComSenhaInputDTO;
import com.lucas.deliveryapi.api.model.usuario.input.UsuarioInputDTO;
import com.lucas.deliveryapi.api.model.usuario.output.UsuarioDTO;
import com.lucas.deliveryapi.domain.model.Usuario;
import com.lucas.deliveryapi.domain.repository.UsuarioRepository;
import com.lucas.deliveryapi.domain.service.UsuarioService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;
import java.util.List;

@AllArgsConstructor
@RestController
@RequestMapping("/usuarios")
public class UsuarioController {

    private UsuarioRepository usuarioRepository;

    private UsuarioService usuarioService;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<UsuarioDTO> list(){
        return mapperDTO.toCollection(usuarioRepository.findAll(), UsuarioDTO.class);
    }

    @GetMapping("/{usuarioId}")
    public UsuarioDTO findById(@PathVariable Long usuarioId){
        return mapperDTO.transform(usuarioService.findById(usuarioId), UsuarioDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public UsuarioDTO addUsuario(@RequestBody @Valid UsuarioComSenhaInputDTO usuarioComSenhaInputDTO){
        var usuario = mapperDTO.transform(usuarioComSenhaInputDTO, Usuario.class);
        usuario = usuarioService.addUsuario(usuario);
        return mapperDTO.transform(usuario, UsuarioDTO.class);
    }

    @PutMapping("/{usuarioId}")
    public UsuarioDTO update(@PathVariable Long usuarioId, @RequestBody @Valid UsuarioInputDTO usuarioInputDTO){
        var usuario = usuarioService.findById(usuarioId);
        mapperDTO.copyToDomain(usuarioInputDTO, usuario);
        usuario = usuarioService.addUsuario(usuario);
        return mapperDTO.transform(usuario, UsuarioDTO.class);
    }

    @PutMapping("/{usuarioId}/senha")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void changePassword(@PathVariable Long usuarioId, @RequestBody @Valid SenhaInputDTO senhaInputDTO){
        usuarioService.changePassword(usuarioId, senhaInputDTO.getSenhaAtual(), senhaInputDTO.getNovaSenha());
    }
}
