package config;

import api.assertions.GoodsAssert;
import api.client.GoodsClient;
import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.logevents.SelenideLogger;
import io.qameta.allure.Step;
import io.qameta.allure.selenide.AllureSelenide;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.extension.ExtendWith;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import ui.pageobject.AdminLoginPage;
import ui.pageobject.AdminProductsPage;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.UUID;
import static com.codeborne.selenide.Selenide.closeWebDriver;

@ExtendWith(SeleniumFailureAttachments.class)
public abstract class BaseTest {
    protected static final TestConfig CONFIG = ConfigReader.load();
    protected final GoodsClient goods = new GoodsClient(CONFIG);
    private final Set<Integer> ids = new LinkedHashSet<>();
    private final Set<String> uiCreatedNames = new LinkedHashSet<>();
    public WebDriver driver;

    @BeforeEach
    @Step("Настроить адрес стенда, ожидания и Allure Selenide Listener")
    void configureTest() {
        Configuration.baseUrl = CONFIG.getWebUrl();
        Configuration.timeout = CONFIG.getTimeout();
        SelenideLogger.addListener("allure", new AllureSelenide()
                .screenshots(true).savePageSource(true).includeSelenideSteps(true));
    }

    @Step("Создать браузер Selenium")
    protected WebDriver createSeleniumDriver() {
        ChromeOptions options = new ChromeOptions();
        if (Configuration.headless) options.addArguments("--headless=new");
        options.addArguments("--window-size=1280,900");
        driver = new ChromeDriver(options);
        return driver;
    }

    @Step("Подготовить уникальное имя товара с префиксом «{prefix}»")
    protected String uniqueName(String prefix) {
        return prefix + " " + UUID.randomUUID();
    }

    @Step("Создать тестовый товар «{name}» и зарегистрировать его для удаления")
    protected int createProduct(String name, BigDecimal price) {
        Response response = goods.add(name, price);
        int id = GoodsAssert.createdProductId(response);
        addForDelete(id);
        return id;
    }

    @Step("Войти в админку с проверкой формы авторизации")
    protected AdminProductsPage signIn(AdminLoginPage loginPage) {
        loginPage.should().isLoaded();
        loginPage.enterUsername(CONFIG.getAdminUsername()).enterPassword(CONFIG.getAdminPassword());
        loginPage.should().usernameIs(CONFIG.getAdminUsername()).passwordIs(CONFIG.getAdminPassword());
        AdminProductsPage adminPage = loginPage.signIn();
        adminPage.should().isLoaded();
        return adminPage;
    }

    @Step("Зарегистрировать товар #{id} для удаления после теста")
    protected void addForDelete(int id) { ids.add(id); }

    @Step("Зарегистрировать создаваемый через UI товар «{name}» для удаления")
    protected void trackUiProduct(String name) { uiCreatedNames.add(name); }

    @Step("Найти созданные через UI товары для удаления")
    private void collectUiProducts() {
        for (String name : uiCreatedNames) {
            boolean found = false;
            for (int page = 0; !found; page++) {
                Response response = goods.list(page, 100);
                GoodsAssert.statusIs(response, 200);
                List<Map<String, Object>> products = response.jsonPath().getList("goods");
                for (Map<String, Object> product : products) {
                    if (name.equals(product.get("name"))) {
                        addForDelete(((Number) product.get("id")).intValue());
                        found = true;
                        break;
                    }
                }
                if (products.size() < 100) break;
            }
        }
    }

    @AfterEach
    @Step("Удалить тестовые данные и закрыть браузеры")
    void tearDown() {
        try {
            collectUiProducts();
            for (int id : ids) {
                // Тест удаления может уже удалить этот товар.
                GoodsAssert.deletedOrAlreadyAbsent(goods.delete(id));
            }
        } finally {
            try {
                if (driver != null) driver.quit();
            } finally {
                closeWebDriver();
                SelenideLogger.removeListener("allure");
            }
        }
    }
}
