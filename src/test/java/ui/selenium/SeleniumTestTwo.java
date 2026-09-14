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

public class SeleniumTestTwo {
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
    @DisplayName("Добавить товар в корзину и проверить, что он отображается.")
    void shouldAddProductToCartAndVerifyItIsDisplayed(){
        driver.findElement(By.cssSelector("[data-action='add-to-cart'][data-name='" + name + "']")).click();
        List<WebElement> toast = driver.findElements(By.cssSelector("[datatest='notification-container']"));
        assertThat(toast)
                .as("Должен появится тост")
                .isNotEmpty();
        assertThat(driver.findElement(By.id("cart-count")).getText())
                .as("Значение каунтера не должно быть равно 0")
                .isNotEqualTo("0");
        driver.findElement(By.id("open-cart-btn")).click();

        List<WebElement> elements = driver.findElements(By.xpath("//b[text()='" + name + "']"));
        assertThat(elements)
                .as("нет товара с именем "+ name)
                .isNotEmpty();

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
