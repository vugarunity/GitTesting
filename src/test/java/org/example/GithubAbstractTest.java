package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class GithubAbstractTest {

    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        // Чтение системных свойств с проверкой
        apiKey = System.getenv("APIKEY");
        baseUrl = System.getenv("BASE_URL");

        System.out.println("API Key: " + apiKey);
        System.out.println("Base URL: " + baseUrl);

        if (apiKey == null || baseUrl == null) {
            throw new IllegalArgumentException("API key or Base URL not provided");
        }

        System.out.println("API Key and Base URL successfully loaded.");
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
