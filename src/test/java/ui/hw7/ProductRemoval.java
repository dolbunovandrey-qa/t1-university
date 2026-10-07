package ui.hw7;

import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class ProductRemoval extends BaseTest {
    @Test
    @DisplayName("Удалить добавленный товар из корзины")
    void testDnD() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = BigDecimal.valueOf(CONFIG.getStartProductPrice());
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.dragToCart(id).openCart();
        page.should().cartIsOpen(1).cartItemIs(id, name, 1, price);
        page.removeFromCart(id);
        page.should().cartIsEmptyAfterRemoving(id);
    }
}
