package api.restassured.task2;

import com.github.javafaker.Faker;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.assertj.core.api.AssertionsForClassTypes;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;

@Tag("api")
public class GoodsList extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();

    public record Good(String name, Double price) {}

    @Test
    @DisplayName("Успешный запрос списка")
    void getGoodsList() {
        Response response = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("goods/add")
                .then()
                .log().all()
                .extract().response();
        addForDelete(response.jsonPath().getInt("data.id"));
        assertThat(response.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);

        Response response2 = given()
                .baseUri(CONFIG.getApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 100)
                .log().all()
                .when()
                .get("goods/list")
                .then()
                .log().all().extract().response();
        assertThat(response2.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        assertThat(response2.jsonPath().getList("goods"))
                .as("Список товаров не должен быть пустым")
                .isNotEmpty();
    }
}
