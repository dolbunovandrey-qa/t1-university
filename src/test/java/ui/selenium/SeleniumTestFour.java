package ui.selenium;

import com.github.javafaker.Faker;
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

public class SeleniumTestFour {

    WebDriver driver;
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name = faker.commerce().productName();
    double price = faker.number().randomDouble(2, 0, 1000);
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
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }
    @Test
    @DisplayName("Проверить сохранение товаров в корзине после обновления страницы.")
    void checkCartItemsAfterReload(){
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
    @AfterEach
    void tearDown(){
        driver.quit();
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
