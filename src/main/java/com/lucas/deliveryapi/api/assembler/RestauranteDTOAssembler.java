package com.lucas.deliveryapi.api.assembler;

import com.lucas.deliveryapi.api.model.restaurante.output.RestauranteDTO;
import com.lucas.deliveryapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.List;
import java.util.stream.Collectors;

@Component
public class RestauranteDTOAssembler {

    @Autowired
    private ModelMapper modelMapper;

    public RestauranteDTO toDTO(Restaurante restaurante){
        return modelMapper.map(restaurante, RestauranteDTO.class);
    }

    public List<RestauranteDTO> toCollection(List<Restaurante> restaurantes){

        return restaurantes.stream()
                .map(this::toDTO)
                .collect(Collectors.toList());
    }
}
