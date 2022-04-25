package com.lucas.deliveryapi.api.model.pedido.output;

import com.lucas.deliveryapi.api.model.formapagamento.output.FormaPagamentoDTO;
import com.lucas.deliveryapi.api.model.restaurante.output.EnderecoDTO;
import com.lucas.deliveryapi.api.model.usuario.output.UsuarioDTO;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.List;

@Setter @Getter
public class PedidoResumoDTO {

    private Long id;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
}
