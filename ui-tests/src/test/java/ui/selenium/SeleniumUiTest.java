package ui.selenium;

import ui.base.UiBaseTest;
import io.qameta.allure.Step;
import org.junit.jupiter.api.Tag;
import ui.pageobject.SeleniumShopPage;

@Tag("ui")
abstract class SeleniumUiTest extends UiBaseTest {
    @Step("Открыть магазин в Selenium")
    protected SeleniumShopPage openShop() {
        return new SeleniumShopPage(createSeleniumDriver(), CONFIG.getWebUrl(), CONFIG.getTimeout()).open();
    }

    @Step("Войти в админку через Selenium с проверкой формы")
    protected void signIn(SeleniumShopPage page) {
        page.openAdmin();
        page.should().loginIsLoaded();
        page.enterUsername(CONFIG.getAdminUsername()).enterPassword(CONFIG.getAdminPassword());
        page.should().credentialsAre(CONFIG.getAdminUsername(), CONFIG.getAdminPassword());
        page.signIn();
        page.should().adminIsLoaded();
    }
}
