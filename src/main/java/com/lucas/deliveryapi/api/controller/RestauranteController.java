package com.lucas.deliveryapi.api.controller;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.lucas.deliveryapi.api.assembler.MapperDTO;
import com.lucas.deliveryapi.api.assembler.RestauranteCadastroDTODisassembler;
import com.lucas.deliveryapi.api.assembler.RestauranteDTOAssembler;
import com.lucas.deliveryapi.api.model.restaurante.input.RestauranteInputDTO;
import com.lucas.deliveryapi.api.model.restaurante.output.RestauranteDTO;
import com.lucas.deliveryapi.domain.exception.BusinessException;
import com.lucas.deliveryapi.domain.exception.EntityNotFoundException;
import com.lucas.deliveryapi.domain.exception.RestaurantNotFoundException;
import com.lucas.deliveryapi.domain.model.Cidade;
import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.model.Restaurante;
import com.lucas.deliveryapi.domain.repository.RestauranteRepository;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.exception.ExceptionUtils;
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

@AllArgsConstructor
@RestController
@RequestMapping("/restaurantes")
public class RestauranteController {

    private RestauranteRepository restauranteRepository;

    private RestauranteService restauranteService;

//    private RestauranteDTOAssembler restauranteDTOAssembler;
//
//    private RestauranteCadastroDTODisassembler restauranteCadastroDTODisassembler;

    private MapperDTO mapperDTO;

    @GetMapping
    public List<RestauranteDTO> list(){
        return mapperDTO.toCollection(restauranteRepository.findAll(), RestauranteDTO.class);
//        return restauranteDTOAssembler.toCollection(restauranteRepository.findAll());
    }

    @GetMapping("/{restauranteId}")
    public RestauranteDTO findById(@PathVariable Long restauranteId){
        var restaurante = restauranteService.findById(restauranteId);
//        return restauranteDTOAssembler.toDTO(restaurante);
        return mapperDTO.transform(restaurante, RestauranteDTO.class);
    }

    @PostMapping
    @ResponseStatus(HttpStatus.CREATED)
    public RestauranteDTO addRestaurante(@RequestBody @Valid RestauranteInputDTO restauranteInputDTO){
        try {
//            var restaurante = restauranteCadastroDTODisassembler.toDomain(restauranteCadastroDTO);
            var restaurante = mapperDTO.transform(restauranteInputDTO, Restaurante.class);
            restaurante = restauranteService.addRestaurante(restaurante);
//            return restauranteDTOAssembler.toDTO(restaurante);
            return mapperDTO.transform(restaurante, RestauranteDTO.class);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @PutMapping("/{restauranteId}")
    public RestauranteDTO update(@PathVariable Long restauranteId,
                                 @RequestBody @Valid RestauranteInputDTO restauranteInputDTO){

        var restaurante = restauranteService.findById(restauranteId);
        restaurante.setCozinha(new Cozinha());
        restaurante.getEndereco().setCidade(new Cidade());
//        restauranteCadastroDTODisassembler.copyToDomain(restauranteCadastroDTO,restaurante);
        mapperDTO.copyToDomain(restauranteInputDTO, restaurante);
        try {
            restaurante = restauranteService.addRestaurante(restaurante);
//            return restauranteDTOAssembler.toDTO(restaurante);
            return mapperDTO.transform(restaurante, RestauranteDTO.class);
        } catch (EntityNotFoundException e) {
            throw new BusinessException(e.getMessage());
        }
    }

    @DeleteMapping("/{restauranteId}")
    public void delete(@PathVariable Long restauranteId){
            restauranteService.delete(restauranteId);
    }

    @PutMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativar(@PathVariable Long restauranteId){
        restauranteService.ativoState(restauranteId);
    }

    @DeleteMapping("/{restauranteId}/ativo")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativar(@PathVariable Long restauranteId){
        restauranteService.ativoState(restauranteId);
    }

    @PutMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void ativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try {
            restauranteService.ativar(restaurantesIds);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }

    @DeleteMapping("/ativacoes")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void inativarMultiplos(@RequestBody List<Long> restaurantesIds){
        try {
            restauranteService.ativar(restaurantesIds);
        } catch (RestaurantNotFoundException e) {
            throw new BusinessException(e.getMessage(), e);
        }
    }


    @PutMapping("/{restauranteId}/abertura")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void open(@PathVariable Long restauranteId){
        restauranteService.abertoState(restauranteId);
    }

    @PutMapping("/{restauranteId}/fechamento")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void close(@PathVariable Long restauranteId){
        restauranteService.abertoState(restauranteId);
    }
//
//    @PatchMapping("/{restauranteId}")
//    public RestauranteDTO updateParcially(@PathVariable Long restauranteId, @RequestBody Map<String, Object> campos,
//                                       HttpServletRequest request){
//        var o = restauranteService.findById(restauranteId);
//
//        merge(campos, o, request);
//        return update(restauranteId, o);
//
//    }
//
//    private void merge(Map<String, Object> camposOrigem, Restaurante restaurante, HttpServletRequest request){
//        ServletServerHttpRequest serverHttpRequest = new ServletServerHttpRequest(request);
//
//        ObjectMapper om = new ObjectMapper();
//        om.configure(DeserializationFeature.FAIL_ON_IGNORED_PROPERTIES,true);
//        om.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES,true);
//
//        try {
//
//            var o = om.convertValue(camposOrigem, Restaurante.class);
//
//            camposOrigem.forEach((k, v) -> {
//                Field field = ReflectionUtils.findField(Restaurante.class, k);
//                field.setAccessible(true);
//
//                Object value = ReflectionUtils.getField(field, o);
//
//                ReflectionUtils.setField(field, restaurante, value);
//            });
//        } catch (IllegalArgumentException e) {
//            Throwable rootCause = ExceptionUtils.getRootCause(e);
//            throw new HttpMessageNotReadableException(e.getMessage(), rootCause, serverHttpRequest);
//        }
//    }

    @GetMapping("/por-nome")
    public List<RestauranteDTO> findByNome() {
        var restaurante = restauranteRepository.find(null, null, new BigDecimal(20));
        return mapperDTO.toCollection(restaurante, RestauranteDTO.class);
    }

    @GetMapping("/frete-gratis")
    public List<RestauranteDTO> restaurantesComFreteGratis(String nome) {
        return mapperDTO.toCollection(restauranteRepository.findComFreteGratis(nome), RestauranteDTO.class);
    }

    @GetMapping("/primeiro")
    public RestauranteDTO primeiroRestaurante(String nome) {
        var restaurante = restauranteRepository.buscarPrimeiro()
                .orElseThrow(() -> new RestaurantNotFoundException("Entidade não encontrada"));
        return mapperDTO.transform(restaurante, RestauranteDTO.class);
    }

}
