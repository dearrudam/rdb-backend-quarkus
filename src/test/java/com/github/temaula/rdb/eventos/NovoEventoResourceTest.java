package com.github.temaula.rdb.eventos;

import io.quarkus.test.junit.QuarkusTest;
import io.restassured.http.ContentType;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import javax.ws.rs.core.Response;
import java.util.Map;
import java.util.stream.Stream;

import static io.restassured.RestAssured.*;
import static org.hamcrest.CoreMatchers.*;

@QuarkusTest
public class NovoEventoResourceTest {

    @Test
    @DisplayName("POST /eventos -> caso de sucesso")
    public void testCasoSucesso() {
        given()
                .contentType(ContentType.JSON)
                .body(Map.of(
                        "nome", "Campanha do agasalho",
                        "descricao", "Doe seus agasalhos para quem precisa",
                        "dataInicio", "2021-01-26",
                        "dataFim", "2021-01-26"
                ))
                .post("/eventos")
                .then()
                .statusCode(Response.Status.OK.getStatusCode())
                .body("id", isA(Number.class))
                .body("nome", is("Campanha do agasalho"))
                .body("descricao", is("Doe seus agasalhos para quem precisa"))
                .body("dataInicio", is("2021-01-26"))
                .body("dataFim", is("2021-01-26"))
                .log().ifValidationFails();

    }

    @ParameterizedTest(name = "{0}")
    @DisplayName("POST /eventos -> caso de falhas de validação")
    @MethodSource("testCasoFalhaValidacaoArgs")
    public void testCasoFalhaValidacao(final String cenario,
                                       final Map<?, ?> body) {
        given()
                .contentType(ContentType.JSON)
                .body(body)
                .post("/eventos")
                .then()
                .statusCode(Response.Status.BAD_REQUEST.getStatusCode())
                .log().ifValidationFails();

    }

    public static Stream<Arguments> testCasoFalhaValidacaoArgs() {
        return Stream.of(
                Arguments.arguments(
                        "nome é requerido - não deve passar nome em branco",
                        Map.of(
                                "nome", "",
                                "descricao", "Doe seus agasalhos para quem precisa",
                                "dataInicio", "2021-01-26",
                                "dataFim", "2021-01-26"
                        )
                ),
                Arguments.arguments(
                        "nome é requerido - não deve omitir o campo nome",
                        Map.of(
                                "descricao", "Doe seus agasalhos para quem precisa",
                                "dataInicio", "2021-01-26",
                                "dataFim", "2021-01-26"
                        )
                ),
                Arguments.arguments(
                        "data inicial é requerida - não deve omitir o campo dataInicio",
                        Map.of(
                                "nome", "Campanha do agasalho",
                                "descricao", "Doe seus agasalhos para quem precisa",
                                //"dataInicio", "2021-01-26",
                                "dataFim", "2021-01-26"
                        )
                ),
                Arguments.arguments(
                        "data inicial é requerida - dataInicio não pode estar em branco",
                        Map.of(
                                "nome", "Campanha do agasalho",
                                "descricao", "Doe seus agasalhos para quem precisa",
                                "dataInicio", "",
                                "dataFim", "2021-01-26"
                        )
                )
        );
    }
}
