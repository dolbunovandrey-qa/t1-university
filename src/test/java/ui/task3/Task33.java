package ui.task3;

import com.codeborne.selenide.Condition;
import com.github.javafaker.Faker;
import config.BaseTest;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import java.util.Locale;

import static com.codeborne.selenide.Selenide.*;
import static io.restassured.RestAssured.given;

public class Task33 extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();
    @BeforeEach
    void setup(){
        open("/");
    }
    @Test
    @DisplayName("3.3. Войти в админку и добавить товар. Проверить уведомление после добавления товара.")
    void verifyAddProductNotification(){
        $("[href='/admin']").click();
        $("#username").sendKeys(CONFIG.getAdminUsername());
        $("#password").sendKeys(CONFIG.getAdminPassword());
        $("[type=submit]").click();
        $("#n-name").sendKeys(name);
        $("#n-price").sendKeys(String.valueOf(price));
        $("#add-btn").click();
        //Получение id для удаления тестовых данных
        addForDelete(Integer.
                parseInt($x("//input[@value='" + name + "']/parent::td/preceding-sibling::td").getText()));
        $("#toast-container").shouldBe(Condition.visible);
        $("#toast-container div").shouldBe(Condition.text("Товар успешно добавлен!"));
    }
}
