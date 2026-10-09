package ui.selenide;

import ui.base.UiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class SelenideTestTwo extends UiBaseTest {
    @Test
    @Tag("smoke")
    @DisplayName("2.2. Добавить товар в корзину и проверить его наличие")
    void shouldAddProductToCartAndVerifyItIsDisplayedTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.addToCart(id);
        page.should().notificationIs(CONFIG.getAddedToCartMessageTemplate().formatted(name, CONFIG.getDefaultQuantity())).cartCountIs(CONFIG.getDefaultQuantity());
        page.openCart().should().cartIsOpen(1).cartItemIs(id, name, CONFIG.getDefaultQuantity(), price).totalPriceIs(price);
    }
}
