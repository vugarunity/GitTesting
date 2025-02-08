package org.example;

import io.restassured.RestAssured;
import org.junit.jupiter.api.BeforeAll;

public class GithubAbstractTest {

    private static String apiKey;
    private static String baseUrl;

    @BeforeAll
    static void initTest() {
        RestAssured.enableLoggingOfRequestAndResponseIfValidationFails();

        apiKey = System.getProperty("apikey");
        baseUrl = System.getProperty("base_url");

        if (apiKey == null || apiKey.isEmpty()) {
            throw new IllegalArgumentException("API key is missing! Set -Dapikey=<your_key> in VM options.");
        }

        if (baseUrl == null || baseUrl.isEmpty()) {
            throw new IllegalArgumentException("Base URL is missing! Set -Dbase_url=<your_url> in VM options.");
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
