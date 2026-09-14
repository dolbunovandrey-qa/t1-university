package ui.selenide;

import com.codeborne.selenide.Selenide;
import com.github.javafaker.Faker;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.Alert;

import java.util.Locale;

import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class SelenideTestFive {
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name = faker.commerce().productName();
    double price = faker.number().randomDouble(2, 100, 1000);
    record Good(String name, double price){};
    int id;
    @BeforeEach
    void setup(){
        Response responsePost = given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .log().all()
                .contentType(ContentType.JSON)
                .body(new SelenideTestFour.Good(name, price))
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
    @DisplayName("2.5. Добавить в корзину товаров более чем на 300 рублей и нажать на кнопку «Оформить заказ»." +
            "Проверить, что отображается JS Alert.")
    void addProductsOver300RublesAndCheckJsAlert(){
        $("[data-name = '" + name + "'] [type = 'number']").setValue("4");
        $("[data-action='add-to-cart'][data-name='" + name + "']").click();
        $("#open-cart-btn").click();
        $("#makeOrder").click();
        switchTo().alert().getText().contains("превышает лимит 300 ₽");
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
