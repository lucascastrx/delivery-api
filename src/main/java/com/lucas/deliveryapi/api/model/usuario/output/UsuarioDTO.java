package com.lucas.deliveryapi.api.model.usuario.output;

import lombok.Getter;
import lombok.Setter;

@Setter @Getter
public class UsuarioDTO {

    private Long id;
    private String nome;
    private String email;
}
