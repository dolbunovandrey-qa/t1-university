package ui.pageobject;

import io.restassured.response.Response;
import static org.assertj.core.api.Assertions.assertThat;

/** Проверки API, используемого только для подготовки и уборки данных. */
public class ProductFixtureAssert extends PageAssert {
    public static void productIsCreated(Response response) {
        requestSucceeded(response);
        assertThat(response.jsonPath().getInt("data.id")).as("ID созданного товара").isPositive();
    }
    public static void requestSucceeded(Response response) {
        assertThat(response.statusCode()).as("HTTP-статус запроса тестовых данных").isEqualTo(200);
    }
}
