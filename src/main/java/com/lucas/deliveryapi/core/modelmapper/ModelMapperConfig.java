package com.lucas.deliveryapi.core.modelmapper;

import com.lucas.deliveryapi.api.model.pedido.input.ItemPedidoInputDTO;
import com.lucas.deliveryapi.api.model.restaurante.output.EnderecoDTO;
import com.lucas.deliveryapi.domain.model.Endereco;
import com.lucas.deliveryapi.domain.model.ItemPedido;
import org.modelmapper.ModelMapper;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;


@Configuration
public class ModelMapperConfig {

    @Bean
    public ModelMapper modelMapper(){
        var modelMapper = new ModelMapper();
        modelMapper.createTypeMap(Endereco.class, EnderecoDTO.class)
                .<String>addMapping(
                        src -> src.getCidade().getEstado().getNome(),
                        (dest, value) -> dest.getCidade().setEstado(value)
                );

        modelMapper.createTypeMap(ItemPedidoInputDTO.class, ItemPedido.class)
                .addMappings(mapper -> mapper.skip(ItemPedido::setId));
        return modelMapper;
    }
}
