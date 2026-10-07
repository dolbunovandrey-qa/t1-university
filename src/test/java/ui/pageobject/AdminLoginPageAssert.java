package ui.pageobject;

import io.qameta.allure.Step;

public class AdminLoginPageAssert extends PageAssert {
    private final AdminLoginPage page;
    public AdminLoginPageAssert(AdminLoginPage page) { this.page = page; }
    @Step("Проверить загрузку страницы и видимость элементов")
    public AdminLoginPageAssert isLoaded() {
        isVisible(page.username, page.password, page.signInButton);
        return this;
    }
    @Step("Проверить значение логина")
    public AdminLoginPageAssert usernameIs(String expected) {
        hasValue(page.username, expected);
        return this;
    }
    @Step("Проверить значение пароля")
    public AdminLoginPageAssert passwordIs(String expected) {
        hasValue(page.password, expected);
        return this;
    }
    @Step("Проверить сообщение о неверных учётных данных")
    public AdminLoginPageAssert credentialsAreRejected() {
        com.codeborne.selenide.Selenide.$("[role='alert']")
                .shouldBe(com.codeborne.selenide.Condition.visible)
                .shouldHave(com.codeborne.selenide.Condition.exactText("Неверные учетные данные пользователя"));
        isLoaded();
        return this;
    }
}
