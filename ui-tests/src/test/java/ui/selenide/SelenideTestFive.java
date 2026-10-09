package ui.selenide;

import ui.base.UiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class SelenideTestFive extends UiBaseTest {
    @Test
    @Tag("smoke")
    @DisplayName("2.5. Проверить JS Alert при заказе дороже 300 рублей")
    void addProductsOver300RublesAndCheckJsAlert() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.setQuantity(id, CONFIG.getOverLimitQuantity());
        page.should().quantityIs(id, CONFIG.getOverLimitQuantity());
        page.addToCart(id).openCart();
        page.should().cartIsOpen(1).totalPriceIs(price.multiply(BigDecimal.valueOf(CONFIG.getOverLimitQuantity())));
        page.placeOrder().should().orderLimitAlertIsShown();
        page.acceptAlert();
    }
}
