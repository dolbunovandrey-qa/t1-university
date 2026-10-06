package ui.pageobject;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Selenide.$;

public class AdminLoginPage {
    final SelenideElement username = $("#username");
    final SelenideElement password = $("#password");
    final SelenideElement signInButton = $("form[action='/login'] button[type='submit']");

    public AdminLoginPage open() {
        Selenide.open("/admin");
        return this;
    }
    public AdminLoginPage enterUsername(String value) { username.setValue(value); return this; }
    public AdminLoginPage enterPassword(String value) { password.setValue(value); return this; }
    public AdminProductsPage signIn() {
        signInButton.click();
        return new AdminProductsPage();
    }
    public AdminLoginPageAssert should() { return new AdminLoginPageAssert(this); }
}
