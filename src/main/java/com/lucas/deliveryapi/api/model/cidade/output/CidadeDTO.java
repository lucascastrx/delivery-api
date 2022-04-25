package com.lucas.deliveryapi.api.model.cidade.output;

import com.lucas.deliveryapi.api.model.estado.output.EstadoDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CidadeDTO {

    private Long id;
    private String nome;
    private EstadoDTO estado;
}
