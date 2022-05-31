package com.lucas.deliveryapi.api.model.pedido.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotNull;

@Setter
@Getter
public class RestauranteIdDTO {

    @NotNull
    private Long id;
}
