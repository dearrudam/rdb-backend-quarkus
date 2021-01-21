package com.temaula.rdb.items;

import io.restassured.http.ContentType;
import io.restassured.http.Header;
import org.junit.jupiter.api.*;

import static org.hamcrest.CoreMatchers.*;
import static org.junit.jupiter.api.Assertions.*;
import io.quarkus.test.junit.QuarkusTest;
import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.inject.Inject;
import javax.sql.DataSource;
import javax.transaction.Transactional;
import java.util.Map;

import static io.restassured.RestAssured.given;

@QuarkusTest
class NovoItemResourceTest  {

    @Test
    @Order(1)
    @DisplayName("testando POST em /itens")
    void execute() {

        given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body(Map.of("data", "ttetete"))
                .when()
                .post("/itens")
                .then()
                .log().ifValidationFails()
                .statusCode(201)
                .and()
                .header("Location", endsWith("/itens/ttetete"));

        given().log().ifValidationFails()
                .contentType(ContentType.JSON)
                .body(Map.of("data", "ttetete"))
                .when()
                .post("/itens")
                .then()
                .log().ifValidationFails()
                .statusCode(400);

    }

    @Test
    @Order(2)
    @DisplayName("testando GET em /itens/ttetete")
    void get() {
        given().log().ifValidationFails()
                .accept(ContentType.JSON)
                .when()
                .get("/itens/ttetete")
                .then()
                .log().ifValidationFails()
                .statusCode(200)
                .body("data",is("ttetete"));

    }

    @Test
    @Order(2)
    @DisplayName("testando GET em /itens/ttetetsdae")
    void get_NotFound() {
        given().log().ifValidationFails()
                .accept(ContentType.JSON)
                .when()
                .get("/itens/ttetetsdae")
                .then()
                .log().ifValidationFails()
                .statusCode(404);

    }
}