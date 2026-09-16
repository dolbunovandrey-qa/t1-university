package ui.selenide;

import com.codeborne.selenide.Condition;
import config.BaseTest;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.*;

public class SelenideTestOne extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();

    @BeforeEach
    void setup(){
        open("/");
    }

    @Test
    @DisplayName("2.1. Добавить товар через админку, выйти на витрину и проверить, что товар отображается.")
    void addProductThroughAdminPanelAndCheckVisibilityOnCatalogTest(){
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
        $("[href='/']").click();
        $("[data-name='" + name + "'] h4").shouldBe(Condition.text(name));
        $("[data-name='" + name + "'] h4+div").shouldBe(Condition.text(String.valueOf(price)));
    }
}
