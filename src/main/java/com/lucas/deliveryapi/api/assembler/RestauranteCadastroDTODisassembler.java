package com.lucas.deliveryapi.api.assembler;

import com.lucas.deliveryapi.api.model.restaurante.input.RestauranteInputDTO;
import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class RestauranteCadastroDTODisassembler {

    @Autowired
    private ModelMapper modelMapper;

    public Restaurante toDomain(RestauranteInputDTO restauranteInputDTO){
        return modelMapper.map(restauranteInputDTO, Restaurante.class);
    }

    public void copyToDomain(RestauranteInputDTO restauranteDTO, Restaurante restaurante ){
        //To associate new reference, so JPA doesnt think we're tryna change id instead of the reference itself
        restaurante.setCozinha(new Cozinha());
        modelMapper.map(restauranteDTO, restaurante);

    }
}
