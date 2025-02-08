package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class GithubAbstractTest {

    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        if (System.getProperty("apikey") != null && System.getProperty("base_url") != null) {
            apiKey = System.getProperty("apikey");
            baseUrl = System.getProperty("base_url");
        }
        else {
            apiKey = System.getenv("APIKEY");
            baseUrl = System.getenv("BASE_URL");
        }
    }

    public static String getApiKey() {
        return apiKey;
    }

    public static String getBaseUrl() {
        return baseUrl;
    }
}
