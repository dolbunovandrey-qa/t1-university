package ui.selenium;

import com.github.javafaker.Faker;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.Locale;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTestOne {

    WebDriver driver;
    private Faker faker = new Faker(Locale.forLanguageTag("ru"));
    String name = faker.commerce().productName();
    double price = faker.number().randomDouble(2, 0, 1000);
    int id;
    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
        driver.get("http://localhost:8080");
    }
    @Test
    @DisplayName("Добавить товар через админку, выйти на витрину и проверить, что товар отображается.")
    void addProductThroughAdminPanelAndCheckVisibilityOnCatalog(){
        driver.findElement(By.cssSelector("[href='/admin']")).click();
        driver.findElement(By.id("username")).sendKeys("admin");
        driver.findElement(By.id("password")).sendKeys("secret123");
        driver.findElement(By.cssSelector("[type=submit]")).click();
        driver.findElement(By.id("n-name")).sendKeys(name);
        driver.findElement(By.id("n-price")).sendKeys(String.valueOf(price));
        driver.findElement(By.id("add-btn")).click();
        //Получение id для удаления тестовых данных
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(1));
        wait.until(ExpectedConditions.presenceOfElementLocated(By.xpath("//input[@value='" + name + "']/parent::td/preceding-sibling::td")));
        String idStr = driver.findElement(By.xpath("//input[@value='" + name + "']/parent::td/preceding-sibling::td")).getText();
        id = Integer.parseInt(idStr);

        driver.findElement(By.cssSelector("[href='/']")).click();
        assertThat(driver.findElement(By.cssSelector("[data-name='" + name + "'] h4")).getText())
                .as("Название должно быть "+name)
                .isEqualTo(name);
        assertThat(driver.findElement(By.cssSelector("[data-name='" + name + "'] h4+div")).getText())
                .as("Цена должна быть = "+ price)
                .isEqualTo(price + " ₽");

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
