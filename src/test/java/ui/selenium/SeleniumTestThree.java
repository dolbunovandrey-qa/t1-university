package ui.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.SeleniumShopPage;

public class SeleniumTestThree extends SeleniumUiTest {
    @Test
    @DisplayName("Selenium: отказать во входе с неверным логином")
    void testLoginWithWrongCredentials() {
        SeleniumShopPage page = openShop().openAdmin();
        page.should().loginIsLoaded();
        page.enterUsername("admins").enterPassword(CONFIG.getAdminPassword());
        page.signIn().should().credentialsAreRejected();
    }
}
