package ui.selenide;

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

public class SelenideTestFive extends BaseTest {
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
    @DisplayName("2.5. Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ»." +
            "Проверить, что отображается JS Alert.")
    void addProductsOver300RublesAndCheckJsAlert(){
        $("[data-name = '" + name + "'] [type = 'number']").setValue("4");
        $("[data-action='add-to-cart'][data-name='" + name + "']").click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        boolean contains = switchTo().alert().getText().contains("превышает лимит 300 ₽");
        assertThat(contains).isTrue();
    }
}
