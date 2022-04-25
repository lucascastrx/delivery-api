package com.lucas.deliveryapi.domain.model;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.lucas.deliveryapi.core.validation.Groups;
import com.lucas.deliveryapi.core.validation.ZeroIncludesDescription;
import lombok.Data;
import lombok.EqualsAndHashCode;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import javax.persistence.*;
import javax.validation.Valid;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;
import javax.validation.groups.ConvertGroup;
import javax.validation.groups.Default;
import java.math.BigDecimal;
import java.time.OffsetDateTime;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@ZeroIncludesDescription(valorField = "taxaFrete", descricaoField = "nome", descricaoObrigatoria = "Frete Grátis")
@Data
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Entity
public class Restaurante {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @EqualsAndHashCode.Include
    private Long id;


    private String nome;
    private BigDecimal taxaFrete;
    private Boolean ativo = Boolean.TRUE;
    private Boolean aberto = Boolean.TRUE;

    @ManyToOne//(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private Cozinha cozinha;

    @Embedded
    private Endereco endereco;

    @CreationTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataCadastro;

    @UpdateTimestamp
    @Column(nullable = false, columnDefinition = "datetime")
    private OffsetDateTime dataAtualizacao;

    @ManyToMany
    @JoinTable(name = "restaurante_forma_pagamento",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "forma_pagamento_id")
    )
    private Set<FormaPagamento> formasPagamento = new HashSet<>();

    @ManyToMany
    @JoinTable(name = "restaurante_usuario_responsavel",
            joinColumns = @JoinColumn(name = "restaurante_id"),
            inverseJoinColumns = @JoinColumn(name = "usuario_id")
    )
    private Set<Usuario> responsaveis = new HashSet<>();

    @OneToMany(mappedBy = "restaurante")
    private List<Produto> produtos = new ArrayList<>();

    public Boolean ativar(){
        setAtivo(true);
        return getAtivo();
    }

    public Boolean inativar(){
        setAtivo(false);
        return getAtivo();
    }

    public Boolean abrir(){
        setAberto(true);
        return getAberto();
    }

    public Boolean fechar(){
        setAberto(false);
        return getAberto();
    }

    public boolean addFormaPagamento(FormaPagamento formaPagamento){
        return getFormasPagamento().add(formaPagamento);
    }

    public boolean removeFormaPagamento(FormaPagamento formaPagamento){
        return getFormasPagamento().remove(formaPagamento);
    }

    public boolean containsFormaPagamento(FormaPagamento formaPagamento){
        return getFormasPagamento().contains(formaPagamento);
    }

    public boolean naoAceitaFormaPagamento(FormaPagamento formaPagamento){
        return !containsFormaPagamento(formaPagamento);
    }

    public boolean addResponsavel(Usuario responsavel){
        return getResponsaveis().add(responsavel);
    }

    public boolean removeResponsavel(Usuario responsavel){
        return getResponsaveis().remove(responsavel);
    }

    public boolean containsResponsavel(Usuario responsavel){
        return getResponsaveis().contains(responsavel);
    }
}
