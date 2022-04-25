package com.lucas.deliveryapi.api.assembler;

import com.lucas.deliveryapi.domain.model.Restaurante;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

@Component
public class MapperDTO {

    @Autowired
    private ModelMapper modelMapper;

    public<T> T transform(Object source, Class<T> clazz){
        return modelMapper.map(source, clazz);
    }

    public void copyToDomain(Object source, Object domain){
        modelMapper.map(source, domain);
    }

    public<T,D> List<D> toCollection(Collection<T> types, Class<D> clazz){
        return types.stream()
                .map(type -> transform(type, clazz))
                .collect(Collectors.toList());
    }
}
