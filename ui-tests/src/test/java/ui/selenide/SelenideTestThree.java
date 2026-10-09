package ui.selenide;

import ui.base.UiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.AdminLoginPage;
import ui.pageobject.MainPage;

@Tag("ui")
public class SelenideTestThree extends UiBaseTest {
    @Test
    @Tag("smoke")
    @DisplayName("2.3. Отказать во входе в админку с неверным логином")
    void testLoginWithWrongCredentialsTest() {
        AdminLoginPage login = new MainPage().open().openAdmin();
        login.should().isLoaded();
        login.enterUsername(CONFIG.getInvalidUsername()).enterPassword(CONFIG.getAdminPassword());
        login.signIn();
        login.should().credentialsAreRejected();
    }
}
