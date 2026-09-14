package ui.selenide;

import com.codeborne.selenide.Condition;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;

public class SelenideTestThree {

    @BeforeEach
    void setup(){
        open("http://localhost:8080");
    }
    @Test
    @DisplayName("2.3. Попытаться войти в админку с неверным логином и паролем.")
    void testLoginWithWrongCredentialsTest(){
        String expectedResult = "Неверные учетные данные пользователя";
        $("[href='/admin']").click();
        $("#username").sendKeys("admins");
        $("#password").sendKeys("secret123");
        $("[type=submit]").click();
        $("[role='alert']").shouldBe(Condition.text(expectedResult));
    }
}
