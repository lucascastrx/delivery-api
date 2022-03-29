package com.lucas.deliveryapi.api.model;

import com.lucas.deliveryapi.domain.model.Cozinha;
import lombok.Data;
import lombok.NonNull;

import java.util.List;

@Data
public class CozinhasXmlWrapper {

    @NonNull
    private List<Cozinha> cozinhas;
}
