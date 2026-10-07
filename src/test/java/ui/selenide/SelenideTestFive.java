package ui.selenide;

import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class SelenideTestFive extends BaseTest {
    @Test
    @DisplayName("2.5. Проверить JS Alert при заказе дороже 300 рублей")
    void addProductsOver300RublesAndCheckJsAlert() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = new BigDecimal("99.12");
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.setQuantity(id, 4);
        page.should().quantityIs(id, 4);
        page.addToCart(id).openCart();
        page.should().cartIsOpen(1).totalPriceIs(price.multiply(BigDecimal.valueOf(4)));
        page.placeOrder().should().orderLimitAlertIsShown();
    }
}
