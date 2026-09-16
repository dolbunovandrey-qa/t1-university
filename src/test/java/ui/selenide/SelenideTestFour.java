package ui.selenide;

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

public class SelenideTestFour extends BaseTest {
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
    @DisplayName("2.4. Проверить сохранение товаров в корзине после обновления страницы.")
    void checkCartItemsAfterReloadTest(){
        $("[data-action='add-to-cart'][data-name='" + name + "']").click();
        $("#toast-container").shouldBe(Condition.visible);
        $("#cart-count").shouldNotBe(Condition.text("0"));
        refresh();
        $("#cart-count").shouldNotBe(Condition.text("0").because("Ожидаем значение отличное от нуля"));
    }
}
