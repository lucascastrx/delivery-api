package com.lucas.deliveryapi.api.model.restaurante.input;

import com.lucas.deliveryapi.api.model.restaurante.output.CidadeResumoDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Setter @Getter
public class EnderecoInputDTO {

    @NotBlank
    private String cep;
    @NotBlank
    private String logradouro;
    @NotBlank
    private String numero;
    @NotBlank
    private String complemento;
    @NotBlank
    private String bairro;
    @Valid
    @NotNull
    private CidadeIdDTO cidade;
}
