package org.example;

import io.qameta.allure.*;
import jdk.jfr.Description;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class DeleteRepositoryTest extends GithubAbstractTest {

    String repositoryName = "vnbsu";

    @Test
    void shouldDeleteRepositorySuccessfully() {

        given()
                .header("Authorization", "token " + getApiKey())
                .when()
                .delete(getBaseUrl() + "/repos/{owner}/{repo}", "vugarunity", repositoryName)
                .then()
                .statusCode(204); 

        int statusCode = given()
                .header("Authorization", "token " + getApiKey())
                .when()
                .get(getBaseUrl() + "/repos/{owner}/{repo}", "vugarunity", repositoryName)
                .getStatusCode();

        assertThat(statusCode).isEqualTo(404); 
    }
}
