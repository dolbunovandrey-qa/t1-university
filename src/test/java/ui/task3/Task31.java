package ui.task3;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Task31 {
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name = faker.commerce().productName();
    double price = faker.number().randomDouble(2, 1, 299);
    record Good(String name, double price){};
    int id;
    @BeforeEach
    void setup(){
        Response responsePost = given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        id = responsePost.jsonPath().getInt("data.id");
        open("http://localhost:8080");
    }
    @Test
    @DisplayName("3.1. Добавить три единицы товара в корзину и оплатить их " +
            "(общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.")
    void checkOrderProcessingNotification(){
        $("[data-action='add-to-cart'][data-name='" + name + "']").click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        $x("//*[@id='toast-container']//div[text()='Заказ принят в обработку!']").shouldBe(Condition.visible);
    }
    @AfterEach
    void tearDown(){
        given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .pathParam("id",id)
                .log().all()
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all();
    }
}
