package com.pruebas.bdd.steps;

import io.cucumber.java.es.Cuando;
import io.cucumber.java.es.Dado;
import io.cucumber.java.es.Entonces;
import io.cucumber.java.es.Y;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

import java.util.HashMap;
import java.util.Map;

import static io.restassured.RestAssured.given;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class RegistroCursoStepDefinitions {

    private Map<String, Object> cursoBody;
    private Response response;

    @Dado("que ingreso los datos del curso {string} con precio {double} y {int} vacantes")
    public void ingresarDatosCurso(String nombre, Double precio, Integer vacantes) {
        RestAssured.baseURI = "http://localhost:8080";
        cursoBody = new HashMap<>();
        cursoBody.put("nombre", nombre);
        cursoBody.put("precio", precio);
        cursoBody.put("vacantes", vacantes);
    }

    @Cuando("envio la solicitud para registrar el curso")
    public void enviarSolicitudRegistro() {
        response = given()
                .contentType(ContentType.JSON)
                .body(cursoBody)
                .when()
                .post("/api/cursos");
    }

    @Entonces("el sistema responde con codigo {int}")
    public void verificarCodigoRespuesta(int codigoEsperado) {
        assertEquals(codigoEsperado, response.getStatusCode());
    }

    @Y("devuelve el nombre del curso {string}")
    public void verificarNombreCurso(String nombreEsperado) {
        String nombreActual = response.jsonPath().getString("nombre");
        assertEquals(nombreEsperado, nombreActual);
    }

    @Entonces("el sistema rechaza el registro con un codigo de error 4xx o 5xx")
    public void verificarCodigoError() {
        int status = response.getStatusCode();
        assertTrue(status >= 400 && status < 600);
    }
}