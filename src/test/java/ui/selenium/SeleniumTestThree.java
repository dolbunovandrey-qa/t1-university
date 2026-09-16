package ui.selenium;

import config.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumTestThree extends BaseTest {
    @BeforeEach
    void setup(){
        driver = new ChromeDriver();
        driver.get(CONFIG.getWebUrl());
    }
    @Test
    @DisplayName("Попытаться войти в админку с неверным логином и паролем.")
    void testLoginWithWrongCredentials(){
        String expectedResult = "Неверные учетные данные пользователя";
        driver.findElement(By.cssSelector("[href='/admin']")).click();
        driver.findElement(By.id("username")).sendKeys("admins");
        driver.findElement(By.id("password")).sendKeys(CONFIG.getAdminPassword());
        driver.findElement(By.cssSelector("[type=submit]")).click();
        assertThat(driver.findElement(By.cssSelector("[role='alert']")).getText())
                .as("неверный текст при ошибке")
                .isEqualTo(expectedResult);
    }
}
