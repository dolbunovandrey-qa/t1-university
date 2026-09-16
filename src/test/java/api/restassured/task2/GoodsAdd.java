package api.restassured.task2;

import com.github.javafaker.Faker;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@Tag("api")
public class GoodsAdd extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();
    public record Good(String name, Double price) {}

    @Test
    @DisplayName("Успешное создание")
    void postGoodsAddSuccess() {
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
        assertThat(response.jsonPath().getString("message"))
                .as("Сообщение не соответствует ожидаемому")
                .isEqualTo("success");
    }
    @Test
    @DisplayName("Проверка валидации имени при создании")
    void postGoodsAddBadRequest() {
        Response response1 = given()
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
        addForDelete(response1.jsonPath().getInt("data.id"));
        assertThat(response1.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);

        Response response2 = given()
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

        assertThat(response2.statusCode())
                .as("Статус код должен быть 400")
                .isEqualTo(400);
        assertThat(response2.jsonPath().getString("message"))
                .as("Сообщение не соответствует ожидаемому")
                .isEqualTo(String.format("Good with name '%s' already exists!",name));
    }

}
