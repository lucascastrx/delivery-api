package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.KitchenNotFoundException;
import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.repository.CozinhaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CozinhaService {

    @Autowired
    private CozinhaRepository cozinhaRepository;

    public Cozinha addCozinha(Cozinha cozinha){
        return cozinhaRepository.save(cozinha);
    }

    public void delete(Long id){
        try {
            cozinhaRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new EntityUnviableException("Entidade está em uso");

        } catch (EmptyResultDataAccessException e){
            throw new KitchenNotFoundException(id);

        }
    }

    public Cozinha findById(Long cozinhaId){
        return cozinhaRepository.findById(cozinhaId)
                .orElseThrow( () -> new KitchenNotFoundException(cozinhaId));


    }

}
