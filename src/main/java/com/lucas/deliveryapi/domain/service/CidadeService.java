package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.CityNotFoundException;
import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.model.Cidade;
import com.lucas.deliveryapi.domain.repository.CidadeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class CidadeService {

    @Autowired
    private CidadeRepository cidadeRepository;

    @Autowired
    private EstadoService estadoService;

    public Cidade addCidade(Cidade cidade){
        var estado = estadoService.findById(cidade.getEstado().getId());

        cidade.setEstado(estado);

        return cidadeRepository.save(cidade);
    }

    public void delete(Long id){
        try {
            cidadeRepository.deleteById(id);
        } catch (DataIntegrityViolationException e) {
            throw new EntityUnviableException("Entidade não pode ser removida pois está em uso");
        } catch (EmptyResultDataAccessException e) {
            throw new CityNotFoundException(id);
        }
    }

    public Cidade findById(Long cidadeId){
       return cidadeRepository.findById(cidadeId)
               .orElseThrow(() -> new CityNotFoundException(cidadeId));
    }
}
