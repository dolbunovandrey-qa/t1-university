package ui.pageobject;

import io.qameta.allure.Step;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class AdminLoginPage extends BasePage {
    final SelenideElement error = $("[role='alert']");
    final SelenideElement username = $("#username");
    final SelenideElement password = $("#password");
    final SelenideElement signInButton = $("form[action='/login'] button[type='submit']");

    @Step("Открыть страницу")
    public AdminLoginPage open() {
        Selenide.open(CONFIG.getAdminPath());
        return this;
    }
    @Step("Ввести логин")
    public AdminLoginPage enterUsername(String value) { username.setValue(value); return this; }
    @Step("Ввести пароль")
    public AdminLoginPage enterPassword(String value) { password.setValue(value); return this; }
    @Step("Нажать кнопку входа")
    public AdminProductsPage signIn() {
        signInButton.click();
        return new AdminProductsPage();
    }
    public AdminLoginPageAssert should() { return new AdminLoginPageAssert(this); }
}
