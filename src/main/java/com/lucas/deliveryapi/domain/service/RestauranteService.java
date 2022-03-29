package com.lucas.deliveryapi.domain.service;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.RestaurantNotFoundException;
import com.lucas.deliveryapi.domain.model.Restaurante;
import com.lucas.deliveryapi.domain.repository.RestauranteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.dao.EmptyResultDataAccessException;
import org.springframework.stereotype.Service;

@Service
public class RestauranteService {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CozinhaService cozinhaService;

    public Restaurante addRestaurante(Restaurante restaurante){
        var cozinha = cozinhaService.findById(restaurante.getCozinha().getId());
        restaurante.setCozinha(cozinha);
        return restauranteRepository.save(restaurante);
    }

    public Restaurante findById(Long id){
       return restauranteRepository.findById(id)
                .orElseThrow(() -> new RestaurantNotFoundException(id) );
    }

    public void delete(Long id){
        try {
            restauranteRepository.deleteById(id);
        } catch (DataIntegrityViolationException e){
            throw new EntityUnviableException("Entidade está em uso");
        } catch (EmptyResultDataAccessException e){
            throw new RestaurantNotFoundException(id);
        }
    }

}
