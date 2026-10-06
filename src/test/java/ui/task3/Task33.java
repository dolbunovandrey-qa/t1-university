package ui.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.AdminProductsPage;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

public class Task33 extends PageObjectTest {
    @Test
    @DisplayName("3.3. Войти в админку, добавить товар и проверить уведомление")
    void verifyAddProductNotification() {
        String name = uniqueName("Новый товар");
        BigDecimal price = new BigDecimal("49.95");
        trackUiProduct(name);

        AdminProductsPage admin = signIn(new MainPage().open().openAdmin());
        admin.enterNewName(name).enterNewPrice(price);
        admin.should().newProductFieldsAre(name, price);
        admin.addProduct();
        admin.should().productIsAdded();
    }
}
