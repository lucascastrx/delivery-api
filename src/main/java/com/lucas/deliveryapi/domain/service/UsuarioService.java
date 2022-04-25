package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.UserNotFoundException;
import com.lucas.deliveryapi.domain.model.Usuario;
import com.lucas.deliveryapi.domain.repository.UsuarioRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class UsuarioService {

    @Autowired
    private UsuarioRepository usuarioRepository;

    @Autowired
    private GrupoService grupoService;

    @Transactional
    public Usuario addUsuario(Usuario usuario){
        usuarioRepository.detach(usuario);

        Optional<Usuario> usuarioOptional = usuarioRepository.findByEmail(usuario.getEmail());

        if(usuarioOptional.isPresent() && !usuarioOptional.get().equals(usuario))
            throw new BusinessException("Email de usuário já cadastrado");

        return usuarioRepository.save(usuario);
    }

    public Usuario findById(Long id){
        return usuarioRepository.findById(id).orElseThrow(()->
                new UserNotFoundException(id));
    }

    @Transactional
    public void delete(Long id){
        try {
            usuarioRepository.deleteById(id);
            usuarioRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityUnviableException("Entidade não está disponível");
        } catch (EmptyResultDataAccessException e) {
            throw new UserNotFoundException(id);
        }
    }

    @Transactional
    public void changePassword(Long id, String senhaAtual, String novaSenha){
        var usuario = findById(id);

        if(usuario.senhaNaoCoincideCom(senhaAtual)){
            throw new BusinessException("Senha atual informada está incorreta.");
        }

        usuario.setSenha(novaSenha);
    }

    @Transactional
    public void addOrRemoveGrupo(Long usuarioId, Long grupoId){
        var usuario = findById(usuarioId);
        var grupo = grupoService.findById(grupoId);

        Boolean result = usuario.containsGrupo(grupo) ? usuario.removeGrupo(grupo) : usuario.addGrupo(grupo);
    }
}
