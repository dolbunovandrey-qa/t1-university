package ui.selenium;

import com.github.javafaker.Faker;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.List;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTestFour extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();

    record Good(String name, double price) {
    }

    @BeforeEach
    void setup() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(), CONFIG.getAdminPassword())
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
        driver = new ChromeDriver();
        driver.get(CONFIG.getWebUrl());
    }

    @Test
    @DisplayName("Проверить сохранение товаров в корзине после обновления страницы.")
    void checkCartItemsAfterReload() {
        driver.findElement(By.cssSelector("[data-action='add-to-cart'][data-name='" + name + "']")).click();
        List<WebElement> toast = driver.findElements(By.cssSelector("[datatest='notification-container']"));
        assertThat(toast)
                .as("Должен появится тост")
                .isNotEmpty();
        assertThat(driver.findElement(By.id("cart-count")).getText())
                .as("Значение каунтера не должно быть равно 0")
                .isNotEqualTo("0");
        driver.navigate().refresh();
        assertThat(driver.findElement(By.id("cart-count")).getText())
                .as("Товар в корзине должен оставаться после рефреша")
                .isNotEqualTo("0");
    }
}
