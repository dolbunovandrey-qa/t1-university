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
import ui.selenide.SelenideTestTwo;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class Task34 extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name2 = faker.commerce().productName();
    record Good(String name, double price){};
    int id;
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
        id = responsePost.jsonPath().getInt("data.id");
        addForDelete(responsePost.jsonPath().getInt("data.id"));
        open("/");
    }
    @Test
    @DisplayName("Войти в админку и отредактировать товар. " +
            "Выйти на список товаров и проверить, что изменения применились.")
    void verifyChangesApplied(){
        $("[href='/admin']").click();
        $("#username").sendKeys(CONFIG.getAdminUsername());
        $("#password").sendKeys(CONFIG.getAdminPassword());
        $("[type=submit]").click();
        $("[type = 'text'][value = '" + name + "']").setValue(name2);
        $("[data-action='update'][data-id ='" + id + "']").click();
        $("[href='/']").click();
        $("#card-"+ id + " h4").shouldBe(Condition.innerText(name2));
    }
}
