package ui.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.SeleniumShopPage;
import java.math.BigDecimal;

public class SeleniumTestFour extends SeleniumUiTest {
    @Test
    @DisplayName("Selenium: проверить сохранение корзины после обновления страницы")
    void checkCartItemsAfterReload() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = BigDecimal.valueOf(CONFIG.getStartProductPrice());
        int id = createProduct(name, price);
        SeleniumShopPage page = openShop();
        page.should().productIsVisible(id, name, price);
        page.addToCart(id);
        page.should().notificationIs(name + " (1 шт.) добавлен в корзину").cartCountIs(1);
        page.refresh();
        // Сохраняем исходное бизнес-ожидание; ошибка стенда попадёт в Allure.
        page.should().cartCountIsNotZero();
        page.openCart().should().cartItemIs(id, name);
    }
}
