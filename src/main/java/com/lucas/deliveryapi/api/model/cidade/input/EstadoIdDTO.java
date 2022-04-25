package com.lucas.deliveryapi.api.model.cidade.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter @Getter
public class EstadoIdDTO {

    @NotNull
    private Long id;
}
