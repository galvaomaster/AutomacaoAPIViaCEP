package tests;

import io.restassured.RestAssured;
import models.CepResponse;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.*;

public class CepApiTests {

    private static final Log log = LogFactory.getLog(CepApiTests.class);

    @Test
    public void consultaCepValido() {
        String cep = "50920135";
        RestAssured.baseURI = "https://viacep.com.br";

        CepResponse response = given()
                .when()
                    .get("/ws/" + cep + "/json/")
                .then()
                    .log().all()
                    .statusCode(200)
                .extract()
                    .as(CepResponse.class);

        assertNotNull(response);
        assertEquals("50920-135", response.cep);
        assertTrue(response.logradouro.contains("Avenida Liberdade"));
        assertEquals("até 596 - lado par", response.complemento);
        assertEquals("", response.unidade);
        assertEquals("Jardim São Paulo", response.bairro);
        assertEquals("Recife", response.localidade);
        assertEquals("PE", response.uf);
        assertEquals("Pernambuco", response.estado);
        assertEquals("Nordeste", response.regiao);
        assertEquals("2611606", response.ibge);
        assertEquals("", response.gia);
        assertEquals("81", response.ddd);
        assertEquals("2531", response.siafi);

    }

    @Test
    public void consultaCepInvalido() {
        String cep = "123456";
        RestAssured.baseURI = "https://viacep.com.br";

        given()
            .when()
                .get("/ws/" + cep + "/json/")
            .then()
                .statusCode(400);
    }

    @Test
    public void consultaCepInexistente() {
        String cep = "99999999";
        RestAssured.baseURI = "https://viacep.com.br";

        CepResponse response = given()
                .when()
                    .get("/ws/" + cep + "/json/")
                .then()
                    .log().all()
                    .statusCode(200)
                    .extract().as(CepResponse.class);

        assertTrue(response.erro);
    }
}