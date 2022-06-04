package com.lucas.deliveryapi;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.KitchenNotFoundException;
import com.lucas.deliveryapi.domain.model.*;
import com.lucas.deliveryapi.domain.service.CidadeService;
import com.lucas.deliveryapi.domain.service.CozinhaService;
import com.lucas.deliveryapi.domain.service.EstadoService;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;
import javax.xml.crypto.Data;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIT {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private RestauranteService restauranteService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private CidadeService cidadeService;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Francesa");

        cozinha = cozinhaService.addCozinha(cozinha);

        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isEqualTo(7);

    }

    @Test
    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);

        DataIntegrityViolationException error = org.junit.jupiter.api.Assertions.assertThrows(DataIntegrityViolationException.class,
                ()-> cozinhaService.addCozinha(cozinha));

        assertThat(error).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){

        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Brazuka");

        Estado estado = new Estado();
        estado.setNome("Ceara");
        estadoService.addEstado(estado);

        Cidade cidade = new Cidade();
        cidade.setNome("Fortaleza");
        cidade.setEstado(estado);
        cidadeService.addCidade(cidade);

        Endereco endereco = new Endereco();
        endereco.setCidade(cidade);
        endereco.setCep("4654646546");
        endereco.setLogradouro("Rua Corvina");
        endereco.setBairro("Bairro das Garças");
        endereco.setNumero("22");

        Restaurante restaurante = new Restaurante();
        restaurante.setEndereco(endereco);
        restaurante.setNome("Delicias Nordestinas");
        restaurante.setTaxaFrete(new BigDecimal(10));
        restaurante.setDataCadastro(OffsetDateTime.now());
        restaurante.setDataAtualizacao(OffsetDateTime.now());



        cozinha = cozinhaService.addCozinha(cozinha);
        System.out.println(cozinha.getId());
        restaurante.setCozinha(cozinha);
        restaurante = restauranteService.addRestaurante(restaurante);
        Long id = cozinha.getId();
        EntityUnviableException error = org.junit.jupiter.api.Assertions.assertThrows(EntityUnviableException.class,
                ()-> cozinhaService.delete(id));

        assertThat(error).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaInexistente(){
        Long id = 999999999L;

        KitchenNotFoundException error = org.junit.jupiter.api.Assertions.assertThrows(KitchenNotFoundException.class,
                ()-> cozinhaService.delete(id));

        assertThat(error).isNotNull();
    }



}
