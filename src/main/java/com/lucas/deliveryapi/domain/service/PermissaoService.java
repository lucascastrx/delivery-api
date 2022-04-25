package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.PermissionNotFoundException;
import com.lucas.deliveryapi.domain.model.Permissao;
import com.lucas.deliveryapi.domain.repository.PermissaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class PermissaoService {

    @Autowired
    private PermissaoRepository permissaoRepository;

    public Permissao findById(Long id){
        return permissaoRepository.findById(id).orElseThrow(()->
                new PermissionNotFoundException(id));
    }


}
