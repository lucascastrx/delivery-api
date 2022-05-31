package com.lucas.deliveryapi.api.model.pedido.input;

import com.lucas.deliveryapi.api.model.restaurante.input.EnderecoInputDTO;
import lombok.Getter;
import lombok.Setter;

import javax.validation.Valid;
import javax.validation.constraints.NotNull;
import java.util.List;

@Setter
@Getter
public class PedidoInputDTO {

    @Valid
    @NotNull
    RestauranteIdDTO restaurante;

    @Valid
    @NotNull
    FormaPagamentoIdDTO formaPagamento;

    @Valid
    @NotNull
    EnderecoInputDTO enderecoEntrega;

    @Valid
    @NotNull
    List<ItemPedidoInputDTO> itens;

}
