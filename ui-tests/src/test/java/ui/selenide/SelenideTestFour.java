package ui.selenide;

import ui.base.UiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class SelenideTestFour extends UiBaseTest {
    @Test
    @DisplayName("2.4. Проверить сохранение корзины после обновления страницы")
    void checkCartItemsAfterReloadTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int id = createProduct(name, price);
        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price);
        page.addToCart(id);
        page.should().cartCountIs(CONFIG.getDefaultQuantity());
        page.refresh();
        // Бизнес-ожидание прежнего теста сохранено: обновление не должно терять корзину.
        page.should().cartCountIsNotZero();
        page.openCart().should().cartIsOpen(1).cartItemIs(id, name, CONFIG.getDefaultQuantity(), price);
    }
}
