package ui.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import java.math.BigDecimal;
import static org.assertj.core.api.Assertions.assertThat;

public class SeleniumShopPageAssert {
    private final SeleniumShopPage page;
    public SeleniumShopPageAssert(SeleniumShopPage page) { this.page = page; }

    @Step("Selenium: проверить видимость формы входа")
    public SeleniumShopPageAssert loginIsLoaded() {
        assertThat(page.element(By.id("username")).isDisplayed()).isTrue();
        assertThat(page.element(By.id("password")).isDisplayed()).isTrue();
        assertThat(page.element(By.cssSelector("button[type='submit']")).isDisplayed()).isTrue();
        return this;
    }

    @Step("Selenium: проверить введённые учётные данные")
    public SeleniumShopPageAssert credentialsAre(String username, String password) {
        assertThat(page.element(By.id("username")).getAttribute("value")).isEqualTo(username);
        assertThat(page.element(By.id("password")).getAttribute("value")).isEqualTo(password);
        return this;
    }

    @Step("Selenium: проверить отказ в авторизации")
    public SeleniumShopPageAssert credentialsAreRejected() {
        assertThat(page.element(By.cssSelector("[role='alert']")).getText())
                .isEqualTo("Неверные учетные данные пользователя");
        loginIsLoaded();
        return this;
    }

    @Step("Selenium: проверить загрузку админки")
    public SeleniumShopPageAssert adminIsLoaded() {
        assertThat(page.element(By.id("n-name")).isDisplayed()).isTrue();
        assertThat(page.element(By.id("n-price")).isDisplayed()).isTrue();
        assertThat(page.element(By.id("add-btn")).isDisplayed()).isTrue();
        return this;
    }

    @Step("Selenium: проверить поля нового товара «{name}», цена {price}")
    public SeleniumShopPageAssert newProductFieldsAre(String name, BigDecimal price) {
        assertThat(page.element(By.id("n-name")).getAttribute("value")).isEqualTo(name);
        assertThat(page.element(By.id("n-price")).getAttribute("value")).isEqualTo(price.toPlainString());
        return this;
    }

    @Step("Selenium: проверить уведомление «{expected}»")
    public SeleniumShopPageAssert notificationIs(String expected) {
        page.wait.until(ExpectedConditions.textToBePresentInElementLocated(By.id("toast-container"), expected));
        assertThat(page.element(By.id("toast-container")).getText()).contains(expected);
        return this;
    }

    @Step("Selenium: проверить карточку товара #{id}: «{name}», цена {price}")
    public SeleniumShopPageAssert productIsVisible(int id, String name, BigDecimal price) {
        assertThat(page.element(By.cssSelector("#card-" + id + " h4")).getText()).isEqualTo(name);
        assertThat(new BigDecimal(page.element(By.cssSelector("#card-" + id + " h4 + div"))
                .getText().replace(" ₽", ""))).isEqualByComparingTo(price);
        return this;
    }

    @Step("Selenium: проверить счётчик корзины {expected}")
    public SeleniumShopPageAssert cartCountIs(int expected) {
        assertThat(page.element(By.id("cart-count")).getText()).isEqualTo(Integer.toString(expected));
        return this;
    }

    @Step("Selenium: проверить сохранение товаров в корзине")
    public SeleniumShopPageAssert cartCountIsNotZero() {
        assertThat(page.element(By.id("cart-count")).getText())
                .as("Товары должны оставаться в корзине после обновления страницы").isNotEqualTo("0");
        return this;
    }

    @Step("Selenium: проверить товар #{id} в корзине: «{name}»")
    public SeleniumShopPageAssert cartItemIs(int id, String name) {
        assertThat(page.element(By.cssSelector("#cart-item-" + id + " b")).getText()).isEqualTo(name);
        return this;
    }
}
