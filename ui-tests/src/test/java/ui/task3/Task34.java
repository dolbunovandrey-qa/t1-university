package ui.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.AdminLoginPage;
import ui.pageobject.AdminProductsPage;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

public class Task34 extends PageObjectTest {
    @Test
    @DisplayName("3.4. Изменить название и цену товара и проверить изменения в каталоге")
    void verifyChangesApplied() {
        String originalName = uniqueName(CONFIG.getStartProductName());
        String updatedName = uniqueName(CONFIG.getUpdatedProductNamePrefix());
        BigDecimal originalPrice = CONFIG.getStartProductPrice();
        BigDecimal updatedPrice = CONFIG.getUpdatedProductPrice();
        int id = createProduct(originalName, originalPrice);

        AdminProductsPage admin = signIn(new AdminLoginPage().open());
        admin.should().productIs(id, originalName, originalPrice);
        admin.editName(id, updatedName).editPrice(id, updatedPrice);
        admin.should().productIs(id, updatedName, updatedPrice);
        admin.saveProduct(id);
        admin.should().productIsSaved(id);

        MainPage catalog = admin.returnToCatalog();
        catalog.should().isLoaded().productIsVisible(id, updatedName, updatedPrice);
    }
}
