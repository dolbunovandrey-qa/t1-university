package api.assertions;

import io.qameta.allure.Step;
import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;

/** Проверяет только готовый ответ, запросов не отправляет. */
public class ApiAssert {
    @Step("Проверить HTTP-статус: {expected}")
    public static void statusIs(Response response, int expected) {
        assertThat(response.statusCode()).as("HTTP-статус").isEqualTo(expected);
    }
    @Step("Проверить сообщение API: {expected}")
    public static void messageIs(Response response, String expected) {
        assertThat(response.jsonPath().getString("message")).as("Сообщение API").isEqualTo(expected);
    }
    @Step("Проверить текст тела ответа: {expected}")
    public static void bodyIs(Response response, String expected) {
        assertThat(response.asString()).as("Тело ответа").isEqualTo(expected);
    }
}
