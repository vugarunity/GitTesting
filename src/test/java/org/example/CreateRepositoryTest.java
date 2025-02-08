package org.example;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class CreateRepositoryTest extends GithubAbstractTest{

    @Test
    void shouldCreateRepoSuccessfully () {

        String requestBody = """
                {
                    "name": "vnbs",
                    "description": "This is your first repository",
                    "private": false
                }
                """;
        
        String responseBody = given()
                .header("Authorization", "token " + getApiKey())
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(getBaseUrl() + "/user/repos")
                .then()
                .statusCode(201)
                .extract()
                .body().asString();

        Assertions.assertTrue(responseBody.contains("\"name\":\"vnbs\""), "Репозиторий был создан.");
    }

    @Test
    void shouldReturn422WhenCreatingRepoWithExistingName() {
        String requestBody = """
                {
                    "name": "vnbs",
                    "description": "This is your first repository",
                    "private": false
                }
                """;
        
        String errorMessage = given()
                .header("Authorization", "token " + getApiKey())
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(getBaseUrl() + "/user/repos")
                .then()
                .statusCode(422) 
                .extract()
                .path("message");

        assertThat(errorMessage).contains("Repository creation failed");
    }

    @Test
    void shouldFailToCreateRepoWithInvalidData() {
        String requestBody = """
                {
                    "name": "vnbs",
                    "description": "This is your first repository",
                    "private": false
                }
                """;
        
        String errorMessage = given()
                .header("Authorization", "token " + getApiKey())
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .post(getBaseUrl() + "/user/repo")
                .then()
                .statusCode(404)
                .extract()
                .path("message");

        assertThat(errorMessage).isEqualTo("Not Found");
    }
}
