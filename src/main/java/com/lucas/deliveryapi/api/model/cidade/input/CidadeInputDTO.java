package com.lucas.deliveryapi.api.model.cidade.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter @Getter
public class CidadeInputDTO {

    @NotBlank
    private String nome;

    @Valid
    @NotNull
    private EstadoIdDTO estado;
}
