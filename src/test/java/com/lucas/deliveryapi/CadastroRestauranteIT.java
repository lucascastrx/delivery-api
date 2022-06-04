package com.lucas.deliveryapi;

import com.lucas.deliveryapi.domain.model.*;
import com.lucas.deliveryapi.domain.repository.CozinhaRepository;
import com.lucas.deliveryapi.domain.repository.RestauranteRepository;
import com.lucas.deliveryapi.domain.service.CidadeService;
import com.lucas.deliveryapi.domain.service.EstadoService;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.web.server.LocalServerPort;
import org.springframework.http.HttpStatus;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import com.lucas.deliveryapi.util.DatabaseCleaner;

import java.math.BigDecimal;
import java.time.OffsetDateTime;

@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
@TestPropertySource("/application.properties")
public class CadastroRestauranteIT {

    @LocalServerPort
    private int port;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    @Autowired
    private RestauranteRepository restauranteRepository;

    @Autowired
    private CidadeService cidadeService;

    @Autowired
    private EstadoService estadoService;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    private final String VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE = "Violação de regra de negócio";

    private final String DADOS_INVALIDOS_PROBLEM_TITLE = "Dados inválidos";

    private final int RESTAURANTE_ID_INEXISTENTE = 1000;

    private Restaurante burgerTopRestaurante;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = "/restaurantes";

        databaseCleaner.clearTables();
        prepararDados();
    }

    @Test
    public void deveRetornarStatus200_QuandoConsultarRestaurantes(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }


//    @Test
//    public void deveRetornarStatus201_QuandoCadastrarRestaurante(){
//        RestAssured
//                .given()
//                    .accept(ContentType.JSON)
//                    .body("""
//                            {
//                                "nome": "New York Barbecue",
//                                    "taxaFrete": 12,
//                                    "cozinha": {
//                                        "id": 2
//                                    },
//                                    "endereco": {
//                                        "cep": "123456789",
//                                        "logradouro": "Rua Oito",
//                                        "numero": "12",
//                                        "complemento": "Casa",
//                                        "bairro": "Nova Holanda",
//                                        "cidade": 1
//                                    }
//                            }
//                            """)
//                    .contentType(ContentType.JSON)
//                .when()
//                    .post()
//                .then()
//                    .statusCode(HttpStatus.CREATED.value());
//    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemTaxaFrete(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .body("""
                                {
                                    "nome": "New York Barbecue",
                                        "cozinha": {
                                            "id": 2
                                        }
                                }
                                """)
                    .contentType(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

    @Test
    public void deveRetornarStatus400_QuandoCadastrarRestauranteSemCozinha(){
        RestAssured
                .given()
                    .accept(ContentType.JSON)
                    .body("""
                                    {
                                        "nome": "New York Barbecue",
                                        "taxaFrete": 17
                                    }
                                    """)
                    .contentType(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.BAD_REQUEST.value())
                    .body("title", Matchers.equalTo(DADOS_INVALIDOS_PROBLEM_TITLE));
    }

//    @Test
//    public void deveRetornarStatus400_QuandoCadastrarRestauranteComCozinhaInexistente(){
//        RestAssured
//                .given()
//                    .accept(ContentType.JSON)
//                    .body("""
//                                        {
//                                            "nome": "New York Barbecue",
//                                            "taxaFrete": 17,
//                                            "cozinha":{
//                                                "id": 200
//                                            }
//                                        }
//                                        """)
//                    .contentType(ContentType.JSON)
//                .when()
//                    .post()
//                .then()
//                    .statusCode(HttpStatus.BAD_REQUEST.value())
//                    .body("title", Matchers.equalTo(VIOLACAO_DE_REGRA_DE_NEGOCIO_PROBLEM_TYPE));
//    }

    @Test
    public void deveRetornarRespostaEStatusCorreto_QuandoConsultarRestauranteExistente(){
        RestAssured
                .given()
                    .pathParam("restauranteId", burgerTopRestaurante.getId())
                    .accept(ContentType.JSON)
                .when()
                    .get("/{restauranteId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", Matchers.equalTo(burgerTopRestaurante.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarRestauranteInexistente(){
        RestAssured
                .given()
                    .pathParam("restauranteId", RESTAURANTE_ID_INEXISTENTE)
                    .accept(ContentType.JSON)
                .when()
                    .get("/{restauranteId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }


    private void prepararDados(){
        Cozinha cozinhaBrasileira = new Cozinha();
        cozinhaBrasileira.setNome("Brasileira");
        cozinhaRepository.save(cozinhaBrasileira);

        Cozinha cozinhaAmericana = new Cozinha();
        cozinhaAmericana.setNome("Americana");
        cozinhaRepository.save(cozinhaAmericana);

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

        burgerTopRestaurante = new Restaurante();
        burgerTopRestaurante.setEndereco(endereco);
        burgerTopRestaurante.setNome("Burger Top");
        burgerTopRestaurante.setTaxaFrete(new BigDecimal(10));
        burgerTopRestaurante.setDataCadastro(OffsetDateTime.now());
        burgerTopRestaurante.setDataAtualizacao(OffsetDateTime.now());
        burgerTopRestaurante.setCozinha(cozinhaAmericana);
        restauranteRepository.save(burgerTopRestaurante);

        Restaurante comidaMineiraRestaurante = new Restaurante();
        comidaMineiraRestaurante.setEndereco(endereco);
        comidaMineiraRestaurante.setNome("Comida Mineira");
        comidaMineiraRestaurante.setTaxaFrete(new BigDecimal(10));
        comidaMineiraRestaurante.setDataCadastro(OffsetDateTime.now());
        comidaMineiraRestaurante.setDataAtualizacao(OffsetDateTime.now());
        comidaMineiraRestaurante.setCozinha(cozinhaBrasileira);
        restauranteRepository.save(comidaMineiraRestaurante);
    }




}
