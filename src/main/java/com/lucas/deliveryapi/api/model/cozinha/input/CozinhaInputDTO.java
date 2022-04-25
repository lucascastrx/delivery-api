package com.lucas.deliveryapi.api.model.cozinha.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter
public class CozinhaInputDTO {

    @NotBlank
    private String nome;
}
