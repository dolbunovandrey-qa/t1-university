package ui.selenide;

import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class SelenideTestTwo extends BaseTest {
    @Test
    @DisplayName("2.2. Добавить товар в корзину и проверить его наличие")
    void shouldAddProductToCartAndVerifyItIsDisplayedTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = BigDecimal.valueOf(CONFIG.getStartProductPrice());
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.addToCart(id);
        page.should().notificationIs(name + " (1 шт.) добавлен в корзину").cartCountIs(1);
        page.openCart().should().cartIsOpen(1).cartItemIs(id, name, 1, price).totalPriceIs(price);
    }
}
