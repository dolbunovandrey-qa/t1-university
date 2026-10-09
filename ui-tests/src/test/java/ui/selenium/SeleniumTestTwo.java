package ui.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.SeleniumShopPage;
import java.math.BigDecimal;

public class SeleniumTestTwo extends SeleniumUiTest {
    @Test
    @DisplayName("Selenium: добавить товар в корзину и проверить его наличие")
    void shouldAddProductToCartAndVerifyItIsDisplayed() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int id = createProduct(name, price);
        SeleniumShopPage page = openShop();
        page.should().productIsVisible(id, name, price);
        page.addToCart(id);
        page.should().notificationIs(CONFIG.getAddedToCartMessageTemplate().formatted(name, CONFIG.getDefaultQuantity())).cartCountIs(CONFIG.getDefaultQuantity());
        page.openCart().should().cartItemIs(id, name);
    }
}
