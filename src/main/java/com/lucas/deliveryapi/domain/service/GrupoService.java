package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.GroupNotFoundException;
import com.lucas.deliveryapi.domain.model.Grupo;
import com.lucas.deliveryapi.domain.repository.GrupoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.validation.GroupDefinitionException;

@Service
public class GrupoService {

    @Autowired
    private GrupoRepository grupoRepository;

    @Autowired
    private PermissaoService permissaoService;

    @Transactional
    public Grupo addGrupo(Grupo grupo){
        return grupoRepository.save(grupo);
    }

    public Grupo findById(Long id){
        return grupoRepository.findById(id).orElseThrow(()->
                new GroupNotFoundException(id));
    }

    @Transactional
    public void delete(Long id){
        try {
            grupoRepository.deleteById(id);
            grupoRepository.flush();
        } catch (DataIntegrityViolationException e) {
            throw new EntityUnviableException("Entidade não está disponível, id: " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new GroupNotFoundException(id);
        }
    }

    @Transactional
    public void addOrRemovePermission(Long grupoId, Long permissaoId){
        var grupo = findById(grupoId);
        var permissao = permissaoService.findById(permissaoId);

        Boolean result = grupo.containsPermissao(permissao) ? grupo.removePermissao(permissao) : grupo.addPermissao(permissao);
    }


}
