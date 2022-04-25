package com.lucas.deliveryapi.api.model.usuario.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter
public class SenhaInputDTO {

    @NotBlank
    private String senhaAtual;

    @NotBlank
    private String novaSenha;
}
