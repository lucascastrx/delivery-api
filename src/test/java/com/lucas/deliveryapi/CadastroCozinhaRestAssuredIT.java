package com.lucas.deliveryapi;

import com.lucas.deliveryapi.domain.model.Cozinha;
import com.lucas.deliveryapi.domain.repository.CozinhaRepository;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import org.apache.catalina.Store;
import org.flywaydb.core.Flyway;
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
import util.DatabaseCleaner;

@ExtendWith({SpringExtension.class})
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT) //o padrão é utilizar o MOCK, ou seja, n levante um web server real
@TestPropertySource("/application-test.properties")
public class CadastroCozinhaRestAssuredIT {

    @LocalServerPort
    private int port;

    @Autowired
    private DatabaseCleaner databaseCleaner;

    @Autowired
    private CozinhaRepository cozinhaRepository;

    private final int ID_INEXISTENTE = 100;
    private Cozinha cozinhaItaliana;

    @BeforeEach
    public void setUp(){
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();
        RestAssured.port = this.port;
        RestAssured.basePath = "/cozinhas";
        RestAssured.given().accept(ContentType.JSON);

        databaseCleaner.clearTables();
        prepararDados();
    }


    @Test
    public void deveRetornarStatus200_QuandoConsultarCozinhas(){

        RestAssured
                 .when()
                    .get()
                .then()
                    .statusCode(HttpStatus.OK.value());
    }

    @Test
    public void deveConter2Cozinhas_QuandoConsultarCozinhas(){

        RestAssured
                 .when()
                    .get()
                .then()
                    .body("", Matchers.hasSize(2))
                    .body("nome", Matchers.hasItems("Italiana", "Espanhola"));


    }

    @Test
    public void deveRetornarStatus201_QuandoCadastrarCozinha(){
        RestAssured
                .given()
                    .body("""
                            { "nome": "Chinesa"
                            } 
                            """)
                    .contentType(ContentType.JSON)
                .when()
                    .post()
                .then()
                    .statusCode(HttpStatus.CREATED.value());
    }

    @Test
    public void deveRetornarRespostaEStatusCorreto_QuandoConsultarCozinhaExistente(){
        RestAssured
                .given()
                    .pathParam("cozinhaId",cozinhaItaliana.getId())
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.OK.value())
                    .body("nome", Matchers.equalTo(cozinhaItaliana.getNome()));
    }

    @Test
    public void deveRetornarStatus404_QuandoConsultarCozinhaInexistente(){
        RestAssured
                .given()
                    .pathParam("cozinhaId",ID_INEXISTENTE)
                .when()
                    .get("/{cozinhaId}")
                .then()
                    .statusCode(HttpStatus.NOT_FOUND.value());
    }

    private void prepararDados(){
        cozinhaItaliana = new Cozinha();
        cozinhaItaliana.setNome("Italiana");
        Cozinha cozinha2 = new Cozinha();
        cozinha2.setNome("Espanhola");

        cozinhaRepository.save(cozinhaItaliana);
        cozinhaRepository.save(cozinha2);
    }
}
