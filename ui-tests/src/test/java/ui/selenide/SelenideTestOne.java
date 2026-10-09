package ui.selenide;

import ui.base.UiBaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import ui.pageobject.AdminProductsPage;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

@Tag("ui")
public class SelenideTestOne extends UiBaseTest {
    @Test
    @Tag("smoke")
    @DisplayName("2.1. Добавить товар через админку и проверить карточку в каталоге")
    void addProductThroughAdminPanelAndCheckVisibilityOnCatalogTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        trackUiProduct(name);
        AdminProductsPage admin = signIn(new MainPage().open().openAdmin());
        admin.enterNewName(name).enterNewPrice(price);
        admin.should().newProductFieldsAre(name, price);
        admin.addProduct();
        admin.should().productIsAdded();
        int id = admin.productId(name);
        admin.returnToCatalog().should().isLoaded().productIsVisible(id, name, price);
    }
}
