package ui.task3;

import com.codeborne.selenide.Condition;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Task31 extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();
    record Good(String name, double price){};
    @BeforeEach
    void setup(){
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
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
        addForDelete(responsePost.jsonPath().getInt("data.id"));
        open("/");
    }
    @Test
    @DisplayName("3.1. Добавить три единицы товара в корзину и оплатить их " +
            "(общая стоимость не должна превышать 300 рублей). Проверить уведомление об обработке заказа.")
    void checkOrderProcessingNotification(){

        $("[data-name = '" + name + "'] [type = 'number']").setValue("3");
        $("[data-action='add-to-cart'][data-name='" + name + "']").click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        $x("//*[@id='toast-container']//div[text()='Заказ принят в обработку!']").shouldBe(Condition.visible);
    }
}
