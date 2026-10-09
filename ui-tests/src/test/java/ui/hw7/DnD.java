package ui.hw7;

import ui.base.UiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class DnD extends UiBaseTest {
    @Test
    @DisplayName("Перетащить товар в корзину и проверить добавление")
    void testDnD() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.dragToCart(id);
        page.should().notificationIs(CONFIG.getAddedToCartMessageTemplate().formatted(name, CONFIG.getDefaultQuantity())).cartCountIs(CONFIG.getDefaultQuantity());
        page.openCart().should().cartItemIs(id, name, CONFIG.getDefaultQuantity(), price);
    }
}
