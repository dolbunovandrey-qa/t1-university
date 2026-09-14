package ui.task3;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class Task33 {
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name = faker.commerce().productName();
    double price = faker.number().randomDouble(2, 0, 1000);
    int id;
    @BeforeEach
    void setup(){
        open("http://localhost:8080");
    }
    @Test
    @DisplayName("3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.")
    void verifyAddProductNotification(){
        $("[href='/admin']").click();
        $("#username").sendKeys("admin");
        $("#password").sendKeys("secret123");
        $("[type=submit]").click();
        $("#n-name").sendKeys(name);
        $("#n-price").sendKeys(String.valueOf(price));
        $("#add-btn").click();
        //Получение id для удаления тестовых данных
        String idStr = $x("//input[@value='" + name + "']/parent::td/preceding-sibling::td").getText();
        id = Integer.parseInt(idStr);
        $("#toast-container").shouldBe(Condition.visible);
        $("#toast-container div").shouldBe(Condition.text("Товар успешно добавлен!"));
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
