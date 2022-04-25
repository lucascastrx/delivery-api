package com.lucas.deliveryapi.api.model.restaurante.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import java.math.BigDecimal;

@Setter @Getter
public class RestauranteInputDTO {

    @NotBlank
    private String nome;

    @NotNull
    @PositiveOrZero
    private BigDecimal taxaFrete;

    @Valid
    @NotNull
    private CozinhaIdDTO cozinha;

    @Valid
    @NotNull
    private EnderecoInputDTO endereco;
}
