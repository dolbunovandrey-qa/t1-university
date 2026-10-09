package ui.pageobject;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.math.BigDecimal;
import java.time.Duration;

/** PageObject для учебных сценариев на чистом Selenium. */
public class SeleniumShopPage extends BasePage {
    final WebDriver driver;
    final WebDriverWait wait;
    private final String webUrl;

    public SeleniumShopPage(WebDriver driver, String webUrl, long timeout) {
        this.driver = driver;
        this.webUrl = webUrl;
        this.wait = new WebDriverWait(driver, Duration.ofMillis(timeout));
    }

    @Step("Selenium: найти видимый элемент {locator}")
    WebElement element(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Selenium: открыть каталог")
    public SeleniumShopPage open() { driver.get(webUrl); return this; }

    @Step("Selenium: перейти к авторизации в админке")
    public SeleniumShopPage openAdmin() { element(By.cssSelector("a[href='/admin']")).click(); return this; }

    @Step("Selenium: ввести логин")
    public SeleniumShopPage enterUsername(String username) {
        WebElement input = element(By.id("username"));
        input.clear();
        input.sendKeys(username);
        return this;
    }

    @Step("Selenium: ввести пароль")
    public SeleniumShopPage enterPassword(String password) {
        WebElement input = element(By.id("password"));
        input.clear();
        input.sendKeys(password);
        return this;
    }

    @Step("Selenium: нажать кнопку входа")
    public SeleniumShopPage signIn() { element(By.cssSelector("button[type='submit']")).click(); return this; }

    @Step("Selenium: ввести название нового товара «{name}»")
    public SeleniumShopPage enterNewName(String name) {
        WebElement input = element(By.id("n-name"));
        input.clear();
        input.sendKeys(name);
        return this;
    }

    @Step("Selenium: ввести цену нового товара {price}")
    public SeleniumShopPage enterNewPrice(BigDecimal price) {
        WebElement input = element(By.id("n-price"));
        input.clear();
        input.sendKeys(price.toPlainString());
        return this;
    }

    @Step("Selenium: создать товар через UI")
    public SeleniumShopPage addProduct() { element(By.id("add-btn")).click(); return this; }

    @Step("Selenium: прочитать ID товара «{name}» в таблице админки")
    public int productId(String name) {
        WebElement row = wait.until(browser -> browser.findElements(By.cssSelector("#tbody tr")).stream()
                .filter(item -> name.equals(item.findElement(By.cssSelector("input[type='text']")).getAttribute("value")))
                .findFirst().orElse(null));
        return Integer.parseInt(row.findElement(By.cssSelector("td")).getText());
    }

    @Step("Selenium: вернуться к каталогу")
    public SeleniumShopPage returnToCatalog() { element(By.cssSelector("a[href='/']")).click(); return this; }

    @Step("Selenium: добавить товар #{id} в корзину")
    public SeleniumShopPage addToCart(int id) {
        element(By.cssSelector("#card-" + id + " [data-action='add-to-cart']")).click();
        return this;
    }

    @Step("Selenium: открыть корзину")
    public SeleniumShopPage openCart() { element(By.id("open-cart-btn")).click(); return this; }

    @Step("Selenium: обновить страницу")
    public SeleniumShopPage refresh() { driver.navigate().refresh(); return this; }

    public SeleniumShopPageAssert should() { return new SeleniumShopPageAssert(this); }
}
