package com.lucas.deliveryapi.api.exceptionhandler;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.List;

@Getter @Setter @Builder @JsonInclude(JsonInclude.Include.NON_NULL)
public class ApiError {
    private Integer status;
    private String type;
    private String title;
    private String detail;

    private String userMessage;
    private LocalDateTime timestamp;

    private List<Field> fields;

    @Getter @Builder
    public static class Field{
        private String name;
        private String userMessage;
    }
}
