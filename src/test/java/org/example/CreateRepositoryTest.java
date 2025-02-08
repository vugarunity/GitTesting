package org.example;

import io.qameta.allure.*;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Epic("Тестирование проекта GitHub")
@Feature("Тестирование GitHub API для репозиториев")
public class CreateRepositoryTest extends GithubAbstractTest{

    @Test
    @DisplayName("Тест shouldCreateRepoSuccessfully - успешное создания репозитория")
    @Description("Данный тест преднозначен для создания репозитория")
    @Link("https://api.github.com/user/repos")
    @Severity(SeverityLevel.NORMAL)
    @Story("Получение нового репозитория")
    @Owner("Ализаде Вугар")
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
    @DisplayName("Тест shouldReturn422WhenCreatingRepoWithExistingName - неуспешное создания репозитория")
    @Description("Тест проверяет, что нельзя создать репозиторий с уже существующим именем")
    @Link("https://api.github.com/user/repos")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Ошибка при повторном создании репозитория")
    @Owner("Ализаде Вугар")
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
    @DisplayName("Тест shouldFailToCreateRepoWithInvalidData - неудачное создание репозитория")
    @Description("Тест проверяет обработку ошибки при создании репозитория с некорректными данными")
    @Link("https://api.github.com/user/repos")
    @Severity(SeverityLevel.TRIVIAL)
    @Story("Ошибка при создании репозитория")
    @Owner("Ализаде Вугар")
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
