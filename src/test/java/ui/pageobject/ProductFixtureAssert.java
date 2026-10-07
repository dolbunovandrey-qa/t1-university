package ui.pageobject;

import io.qameta.allure.Step;

import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;

/** Проверки API, используемого только для подготовки и уборки данных. */
public class ProductFixtureAssert extends PageAssert {
    @Step("Проверить создание тестового товара")
    public static void productIsCreated(Response response) {
        requestSucceeded(response);
        assertThat(response.jsonPath().getInt("data.id")).as("ID созданного товара").isPositive();
    }
    @Step("Проверить HTTP-статус 200")
    public static void requestSucceeded(Response response) {
        assertThat(response.statusCode()).as("HTTP-статус запроса тестовых данных").isEqualTo(200);
    }
}
