package com.lucas.deliveryapi.api.model.grupo.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter
public class GrupoInputDTO {

    @NotBlank
    private String nome;
}
