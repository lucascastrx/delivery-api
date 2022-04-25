package com.lucas.deliveryapi.api.model.restaurante.output;

import com.lucas.deliveryapi.api.model.cozinha.output.CozinhaDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

@Setter @Getter
public class RestauranteDTO {

    private Long id;
    private String nome;
    private BigDecimal taxaFrete;
    private Boolean ativo;
    private Boolean aberto;
    private CozinhaDTO cozinha;
    private EnderecoDTO endereco;
}
