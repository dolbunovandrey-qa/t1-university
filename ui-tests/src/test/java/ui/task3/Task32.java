package ui.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

public class Task32 extends PageObjectTest {
    @Test
    @DisplayName("3.2. Проверить сумму разных товаров с учётом их количества")
    void verifyCartTotalPrice() {
        String firstName = uniqueName(CONFIG.getStartProductName());
        String secondName = uniqueName(CONFIG.getStartProductName());
        BigDecimal firstPrice = CONFIG.getFirstCartPrice();
        BigDecimal secondPrice = CONFIG.getSecondCartPrice();
        int firstQuantity = CONFIG.getFirstCartQuantity();
        int secondQuantity = CONFIG.getSecondCartQuantity();
        int firstId = createProduct(firstName, firstPrice);
        int secondId = createProduct(secondName, secondPrice);
        BigDecimal firstTotal = firstPrice.multiply(BigDecimal.valueOf(firstQuantity));
        BigDecimal secondTotal = secondPrice.multiply(BigDecimal.valueOf(secondQuantity));

        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(firstId, firstName, firstPrice)
                .productIsVisible(secondId, secondName, secondPrice).cartCountIs(0);
        page.increaseQuantity(firstId).decreaseQuantity(firstId).setQuantity(firstId, firstQuantity);
        page.should().quantityIs(firstId, firstQuantity);
        page.setQuantity(secondId, secondQuantity);
        page.should().quantityIs(secondId, secondQuantity);
        page.addToCart(firstId).addToCart(secondId).openCart();
        page.should().cartIsOpen(2).cartCountIs(firstQuantity + secondQuantity)
                .cartItemIs(firstId, firstName, firstQuantity, firstTotal)
                .cartItemIs(secondId, secondName, secondQuantity, secondTotal)
                .totalPriceIs(firstTotal.add(secondTotal));
        page.closeCart();
        page.should().cartIsClosed();
    }
}
