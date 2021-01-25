package com.temaula.rdb.itens;

import io.restassured.http.ContentType;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;

import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;

import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
class NovoItemResourceTest {

    static final Map body = Map.of("data", "ttetete");

    @Test
    @Order(1)
    @DisplayName("adicionar Itens")
    void test1() {

        assertDoesNotThrow(() -> {
            given().log().ifValidationFails()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post("/itens")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(201)
                    .and()
                    .header("Location", endsWith("/itens/" + body.get("data")));
        }, "deve permitir criar um item novo " + body);

        assertDoesNotThrow(() -> {

            given().log().ifValidationFails()
                    .contentType(ContentType.JSON)
                    .body(body)
                    .when()
                    .post("/itens")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(400);

        }, "não deve permitir adicionar um item com a mesma descricão");

        assertDoesNotThrow(() -> {
            given().log().ifValidationFails()
                    .accept(ContentType.JSON)
                    .when()
                    .get("/itens/" + body.get("data"))
                    .then()
                    .log().ifValidationFails()
                    .statusCode(200)
                    .body("data", is(body.get("data")));
        }, "deve retornar o item cadastrado");
        assertDoesNotThrow(() -> {
            given().log().ifValidationFails()
                    .accept(ContentType.JSON)
                    .when()
                    .get("/itens/ttetetsdae")
                    .then()
                    .log().ifValidationFails()
                    .statusCode(404);
        },"deve retornar 404 NOT FOUND quando solicitado um item não registrado");

    }

}