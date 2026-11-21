package com.pruebas.api;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.*;

public class CursoApiTest {

    @BeforeAll
    static void init() {
        RestAssured.baseURI = "http://localhost:8080";
    }

    @Test
    void testConsultarTodosLosCursos() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/cursos")
                .then()
                .statusCode(200)
                .body("size()", greaterThanOrEqualTo(3));
    }

    @Test
    void testConsultarCursoPorId() {
        given()
                .contentType("application/json")
                .when()
                .get("/api/cursos/1")
                .then()
                .statusCode(200)
                .body("nombre", equalTo("Pruebas de Software"))
                .body("vacantes", greaterThan(0));
    }
}