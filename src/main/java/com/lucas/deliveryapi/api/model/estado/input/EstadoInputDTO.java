package com.lucas.deliveryapi.api.model.estado.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter
public class EstadoInputDTO {

    @NotBlank
    private String nome;
}
