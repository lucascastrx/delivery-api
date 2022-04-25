package com.lucas.deliveryapi;

import com.lucas.deliveryapi.domain.exception.EntityUnviableException;
import com.lucas.deliveryapi.domain.exception.KitchenNotFoundException;
import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.model.Restaurante;
import com.lucas.deliveryapi.domain.service.CozinhaService;
import com.lucas.deliveryapi.domain.service.RestauranteService;
import org.assertj.core.api.Assertions;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;

import javax.validation.ConstraintViolationException;

import static org.assertj.core.api.Assertions.assertThat;

@ExtendWith(SpringExtension.class)
@SpringBootTest
class CadastroCozinhaIT {

    @Autowired
    private CozinhaService cozinhaService;

    @Autowired
    private RestauranteService restauranteService;

    @BeforeEach
    public void setUp(){

    }

    @Test
    public void deveAtribuirId_QuandoCadastrarCozinhaComDadosCorretos(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome("Francesa");

        cozinha = cozinhaService.addCozinha(cozinha);

        assertThat(cozinha).isNotNull();
        assertThat(cozinha.getId()).isEqualTo(18);

    }

    @Test
    public void deveFalhar_QuandoCadastrarCozinhaSemNome(){
        Cozinha cozinha = new Cozinha();
        cozinha.setNome(null);

        ConstraintViolationException error = org.junit.jupiter.api.Assertions.assertThrows(ConstraintViolationException.class,
                ()-> cozinhaService.addCozinha(cozinha));

        assertThat(error).isNotNull();
    }

    @Test
    public void deveFalhar_QuandoExcluirCozinhaEmUso(){
        var ref = new Object() {
            Cozinha cozinha = new Cozinha();
        };
        Restaurante restaurante = new Restaurante();

        ref.cozinha = cozinhaService.addCozinha(ref.cozinha);
        restaurante.setCozinha(ref.cozinha);
        restaurante = restauranteService.addRestaurante(restaurante);

        EntityUnviableException error = org.junit.jupiter.api.Assertions.assertThrows(EntityUnviableException.class,
                ()-> cozinhaService.delete(ref.cozinha.getId()));

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
