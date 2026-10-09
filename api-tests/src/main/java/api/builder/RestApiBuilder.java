package api.builder;

import config.ConfigReader;
import config.TestConfig;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.RestAssured;
import io.restassured.config.HttpClientConfig;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

/** Настраивает запросы. Конкретных эндпоинтов здесь нет. */
public class RestApiBuilder {
    private final TestConfig config = ConfigReader.load();

    public RequestSpecification build() {
        RequestSpecification request = given().baseUri(config.getApiUrl())
                .config(RestAssured.config().httpClient(HttpClientConfig.httpClientConfig()
                        .setParam("http.connection.timeout", Math.toIntExact(config.getTimeout()))
                        .setParam("http.socket.timeout", Math.toIntExact(config.getTimeout()))))
                .filter(new AllureRestAssured())
                .auth().preemptive().basic(config.getAdminUsername(), config.getAdminPassword())
                .log().ifValidationFails();
        if ("DEBUG".equalsIgnoreCase(config.getLogging())) request.log().all();
        return request;
    }
}
