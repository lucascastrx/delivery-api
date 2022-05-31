package com.lucas.deliveryapi.domain.model;

import lombok.Data;
import lombok.EqualsAndHashCode;

import javax.persistence.*;
import java.math.BigDecimal;

@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class ItemPedido {

    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;

    private Integer quantidade;
    private BigDecimal precoUnitario;
    private BigDecimal precoTotal;
    private String observacao;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Pedido pedido;

    @ManyToOne
    @JoinColumn(nullable = false)
    private Produto produto;


    public void calcularPrecoTotal(){
        BigDecimal precoUnitario = getPrecoUnitario();
        Integer quantidade = getQuantidade();

        if (precoUnitario == null) precoUnitario = BigDecimal.ZERO;

        if(quantidade == null) quantidade = 0;

        setPrecoTotal(precoUnitario.multiply(new BigDecimal(quantidade)));
    }
}
