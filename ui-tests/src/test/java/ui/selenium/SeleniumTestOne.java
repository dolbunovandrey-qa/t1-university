package ui.selenium;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.SeleniumShopPage;
import java.math.BigDecimal;

public class SeleniumTestOne extends SeleniumUiTest {
    @Test
    @DisplayName("Selenium: добавить товар через админку и проверить каталог")
    void addProductThroughAdminPanelAndCheckVisibilityOnCatalog() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        trackUiProduct(name);
        SeleniumShopPage page = openShop();
        signIn(page);
        page.enterNewName(name).enterNewPrice(price);
        page.should().newProductFieldsAre(name, price);
        page.addProduct();
        page.should().notificationIs(CONFIG.getProductAddedNotification());
        int id = page.productId(name);
        page.returnToCatalog().should().productIsVisible(id, name, price);
    }
}
