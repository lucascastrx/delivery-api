package com.lucas.deliveryapi.api.model.restaurante.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter @Getter
public class CozinhaIdDTO {

    @NotNull
    private Long id;
}
