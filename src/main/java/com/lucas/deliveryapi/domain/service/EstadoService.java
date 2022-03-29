package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.StateNotFoundException;
import com.lucas.deliveryapi.domain.model.Estado;
import com.lucas.deliveryapi.domain.repository.EstadoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class EstadoService {

    @Autowired
    private EstadoRepository estadoRepository;

    public Estado addEstado(Estado estado){
        return estadoRepository.save(estado);
    }

    public Estado findById(Long id){
        return estadoRepository.findById(id).orElseThrow(() ->
                new StateNotFoundException(id));
    }


    public void delete(Long id){
        try {
            estadoRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityUnviableException("Entidade está em uso " + id);
        } catch (EmptyResultDataAccessException e) {
            throw new StateNotFoundException(id);
        }
    }
}
