package com.lucas.deliveryapi.api.model.formapagamento.input;

import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

@Setter @Getter
public class FormaPagamentoInputDTO {

    @NotBlank
    private String descricao;
}
