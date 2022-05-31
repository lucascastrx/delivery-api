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
public class PedidoDTO {

    private String codigo;
    private BigDecimal subtotal;
    private BigDecimal taxaFrete;
    private BigDecimal valorTotal;
    private String status;
    private OffsetDateTime dataCriacao;
    private OffsetDateTime dataConfirmacao;
    private OffsetDateTime dataEntrega;
    private OffsetDateTime dataCancelamento;
    private RestauranteResumoDTO restaurante;
    private UsuarioDTO cliente;
    private FormaPagamentoDTO formaPagamento;
    private EnderecoDTO enderecoEntrega;
    private List<ItemPedidoDTO> itens;
}