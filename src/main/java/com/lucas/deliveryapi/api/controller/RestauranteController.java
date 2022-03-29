package com.lucas.deliveryapi.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.EntityNotFoundException;
import com.lucas.deliveryapi.domain.exception.RestaurantNotFoundException;
import com.lucas.deliveryapi.domain.model.Restaurante;
import com.lucas.deliveryapi.domain.repository.RestauranteRepository;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import org.apache.commons.lang3.exception.ExceptionUtils;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.converter.HttpMessageNotReadableException;
import org.springframework.http.server.ServletServerHttpRequest;
import org.springframework.util.ReflectionUtils;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import javax.validation.Valid;
import java.lang.reflect.Field;
import java.math.BigDecimal;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private RestauranteService restauranteService;

    @GetMapping
    public List<Restaurante> list(){
        return restauranteRepository.findAll();
    }

    @GetMapping("/{restauranteId}")
    public Restaurante findById(@PathVariable Long restauranteId){
        return restauranteService.findById(restauranteId);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public Restaurante addRestaurante(@RequestBody @Valid Restaurante restaurante){
        try {
            return restauranteService.addRestaurante(restaurante);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public Restaurante update(@PathVariable Long restauranteId, @RequestBody @Valid Restaurante restaurante){
        var o = restauranteService.findById(restauranteId);
        BeanUtils.copyProperties(restaurante, o,
                "id", "formasPagamento", "endereco", "dataCadastro", "produtos");
        try {
            return restauranteService.addRestaurante(o);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @DeleteMapping("/{restauranteId}")
    public void delete(@PathVariable Long restauranteId){
            restauranteService.delete(restauranteId);
    }

    @PatchMapping("/{restauranteId}")
    public Restaurante updateParcially(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
                                       HttpServletRequest request){
        var o = restauranteService.findById(restauranteId);

        merge(campos, o, request);
        return update(restauranteId, o);

    }

    private void merge(Map<String, Object> camposOrigem, Restaurante restaurante, HttpServletRequest request){
        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);

        ObjectMapper om = new ObjectMapper();
        om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);

        try {

            var o = om.convertValue(camposOrigem, Restaurante.class);

            camposOrigem.forEach((k, v) -> {
                Field field = ReflectionUtils.findField(Restaurante.class, k);
                field.setAccessible(true);

                Object value = ReflectionUtils.getField(field, o);

                ReflectionUtils.setField(field, restaurante, value);
            });
        } catch (IllegalArgumentException e) {
            Throwable rootCause = ExceptionUtils.getRootCause(e);
            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
        }
    }

    @GetMapping("/por-nome")
    public List<Restaurante> findByNome() {
        return restauranteRepository.find(null, null, new BigDecimal(20));
    }

    @GetMapping("/frete-gratis")
    public List<Restaurante> restaurantesComFreteGratis(String nome) {

        return restauranteRepository.findComFreteGratis(nome);
    }

    @GetMapping("/primeiro")
    public Restaurante primeiroRestaurante(String nome) {
        return restauranteRepository.buscarPrimeiro()
                .orElseThrow(() -> new RestaurantNotFoundException("Entidade não encontrada"));
    }

}
