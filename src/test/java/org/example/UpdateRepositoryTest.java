package org.example;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class UpdateRepositoryTest extends GithubAbstractTest {

    @Test
    void shouldUpdateRepoSuccessfully() {

        String requestBody = """
                {
                    "name": "vnbsu",
                    "description": "This is your first updated repository",
                    "private": false
                }
                """;
        
        String responseBody = given()
                .header("Authorization", "token " + getApiKey())
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch(getBaseUrl() + "/repos/vugarunity/vnbs")
                .then()
                .statusCode(200) 
                .extract()
                .body().asString();

        Assertions.assertTrue(responseBody.contains("\"name\":\"vnbsu\""), "Репозиторий был обновлен.");
    }

    @Test
    void shouldFailToUpdateRepoWithInvalidData() {

        String requestBody = """
                {
                    "name": "vnbsu",
                    "description": "This is your first updated repository",
                    "private": false
                }
                """;

        String errorMessage = given()
                .header("Authorization", "token " + getApiKey())
                .header("Content-Type", "application/json")
                .body(requestBody)
                .when()
                .patch(getBaseUrl() + "/repos/vugarunity/vnb")
                .then()
                .statusCode(404) 
                .extract()
                .path("message");

        assertThat(errorMessage).isEqualTo("Not Found");
    }
}
