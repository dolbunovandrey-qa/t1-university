package ui.hw7;

import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class DnD extends BaseTest {
    @Test
    @DisplayName("Перетащить товар в корзину и проверить добавление")
    void testDnD() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = BigDecimal.valueOf(CONFIG.getStartProductPrice());
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.dragToCart(id);
        page.should().notificationIs(name + " (1 шт.) добавлен в корзину").cartCountIs(1);
        page.openCart().should().cartItemIs(id, name, 1, price);
    }
}
