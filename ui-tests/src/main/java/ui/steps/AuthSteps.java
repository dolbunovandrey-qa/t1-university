package ui.steps;

import config.ConfigReader;
import config.TestConfig;
import io.qameta.allure.Step;
import ui.pageobject.AdminLoginPage;
import ui.pageobject.AdminProductsPage;

public class AuthSteps {
    private final TestConfig CONFIG = ConfigReader.load();
    @Step("Войти в админку с проверкой формы авторизации")
    public AdminProductsPage signIn(AdminLoginPage loginPage) {
        loginPage.should().isLoaded();
        loginPage.enterUsername(CONFIG.getAdminUsername()).enterPassword(CONFIG.getAdminPassword());
        loginPage.should().usernameIs(CONFIG.getAdminUsername()).passwordIs(CONFIG.getAdminPassword());
        AdminProductsPage adminPage = loginPage.signIn();
        adminPage.should().isLoaded();
        return adminPage;
    }

}
