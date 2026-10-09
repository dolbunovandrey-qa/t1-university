package ui.base;

import api.steps.ProductSteps;
import config.BaseTest;
import config.SeleniumFailureAttachments;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.Dimension;
import ui.pageobject.AdminLoginPage;
import ui.pageobject.AdminProductsPage;
import ui.steps.AuthSteps;
import java.math.BigDecimal;
import static com.codeborne.selenide.Selenide.closeWebDriver;

@ExtendWith(SeleniumFailureAttachments.class)
public abstract class UiBaseTest extends BaseTest {
    private final ProductSteps products = new ProductSteps();
    public WebDriver driver;

    @BeforeEach
    @Step("Настроить браузер и Allure Selenide Listener")
    void configureTest() {
        Configuration.baseUrl = CONFIG.getWebUrl();
        Configuration.timeout = CONFIG.getTimeout();
        Configuration.browser = CONFIG.getBrowser();
        Configuration.browserSize = CONFIG.getBrowserSize();
        Configuration.headless = CONFIG.getHeadless();
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true).savePageSource(true).includeSelenideSteps(true));
    }
    @Step("Создать браузер Selenium")
    protected WebDriver createSeleniumDriver() {
        switch (CONFIG.getBrowser().toLowerCase(java.util.Locale.ROOT)) {
            case "chrome" -> {
                ChromeOptions options = new ChromeOptions();
                if (CONFIG.getHeadless()) options.addArguments("--headless=new");
                driver = new ChromeDriver(options);
            }
            case "edge" -> {
                EdgeOptions options = new EdgeOptions();
                if (CONFIG.getHeadless()) options.addArguments("--headless=new");
                driver = new EdgeDriver(options);
            }
            case "firefox" -> {
                FirefoxOptions options = new FirefoxOptions();
                if (CONFIG.getHeadless()) options.addArguments("-headless");
                driver = new FirefoxDriver(options);
            }
            default -> throw new IllegalArgumentException("Неизвестный Selenium-браузер: " + CONFIG.getBrowser());
        }
        String[] size = CONFIG.getBrowserSize().split("x");
        driver.manage().window().setSize(new Dimension(Integer.parseInt(size[0]), Integer.parseInt(size[1])));
        return driver;
    }
    @Step("Подготовить товар «{name}»")
    protected int createProduct(String name, BigDecimal price) { return products.createProduct(name, price); }
    @Step("Зарегистрировать создаваемый через UI товар «{name}»")
    protected void trackUiProduct(String name) { products.trackUiProduct(name); }
    @Step("Войти в админку")
    protected AdminProductsPage signIn(AdminLoginPage loginPage) { return new AuthSteps().signIn(loginPage); }
    @AfterEach
    @Step("Удалить тестовые данные и закрыть браузеры")
    void tearDown() {
        try { products.cleanup(); }
        finally {
            try { if (driver != null) driver.quit(); }
            finally {
                closeWebDriver();
                SelenideLogger.removeListener("allure");
            }
        }
    }
}
