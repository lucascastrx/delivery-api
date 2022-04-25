package com.lucas.deliveryapi.api.model.restaurante.output;

import com.lucas.deliveryapi.api.model.cidade.output.CidadeDTO;
import lombok.Getter;
import lombok.Setter;

import javax.persistence.Column;

@Setter @Getter
public class EnderecoDTO {

    private String cep;
    private String logradouro;
    private String numero;
    private String complemento;
    private String bairro;
    private CidadeResumoDTO cidade;
}
