package com.lucas.deliveryapi.api.exceptionhandler;

import lombok.Getter;

@Getter
public enum ErrorType {
    RESOURCE_NOT_FOUND("/resource-not-found", "Recurso não encontrado"),
    ENTITY_UNVIABLE("/entity-unviable", "Entidade em uso"),
    BUSINESS_ERROR("/business_error", "Violação de regra de negócio"),
    INCOMPREHENSIVE_MESSAGE("/incomprehensive-message","Mensagem incompreensível"),
    INVALID_PARAM("/invalid-param", "Parâmetro inválido"),
    SYSTEM_ERROR("/system-error","Erro de sistema"),
    INVALID_DATA("/invalid-data","Dados inválidos");


    private String title;
    private String uri;

    ErrorType(String path, String title) {
        this.title = title;
        this.uri = "https://delivery.com"+path;
    }
}
