package com.lucas.deliveryapi.api.model.permissao.output;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class PermissaoDTO {

    private Long id;
    private String nome;
    private String descricao;
}
