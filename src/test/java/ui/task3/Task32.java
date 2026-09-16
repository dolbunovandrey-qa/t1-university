package ui.task3;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.List;
import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Task32 extends BaseTest {
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name1 = faker.commerce().productName();
    String name2 = faker.commerce().productName();
    double price1 = faker.number().randomDouble(2, 1, 299);
    double price2 = faker.number().randomDouble(2, 1, 299);
    record Good(String name, double price){};
    double totalPrice = price1 + price2;
    @BeforeEach
    void setup(){
        Response responsePost1 = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name1, price1))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost1.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        addForDelete(responsePost1.jsonPath().getInt("data.id"));
        Response responsePost2 = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name2, price2))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost2.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        addForDelete(responsePost2.jsonPath().getInt("data.id"));
        open("/");
    }
    @Test
    @DisplayName("3.2. Добавить в корзину несколько разных товаров и проверить, " +
            "что общая цена в корзине считается корректно.")
    void verifyCartTotalPrice(){
        $("[data-action='add-to-cart'][data-name='" + name1 + "']").click();
        $("[data-action='add-to-cart'][data-name='" + name2 + "']").click();
        $("#open-cart-btn").click();
        $("#total-price").shouldBe(Condition.text(String.valueOf(totalPrice)));
    }
}
