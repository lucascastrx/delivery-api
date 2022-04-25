package com.lucas.deliveryapi.api.model.restaurante.output;

import com.lucas.deliveryapi.api.model.estado.output.EstadoDTO;
import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class CidadeResumoDTO {

    private Long id;
    private String nome;
    private String estado;
}
