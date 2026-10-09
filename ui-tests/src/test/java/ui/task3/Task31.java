package ui.task3;

import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.Tag;
import ui.pageobject.MainPage;
import java.math.BigDecimal;

public class Task31 extends PageObjectTest {
    @Test
    @Tag("smoke")
    @DisplayName("3.1. Оплатить три единицы товара стоимостью до 300 рублей")
    void checkOrderProcessingNotification() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int quantity = CONFIG.getOrderQuantity();
        int id = createProduct(name, price);
        BigDecimal total = price.multiply(BigDecimal.valueOf(quantity));

        MainPage page = new MainPage().open();
        page.should().isLoaded().productIsVisible(id, name, price).quantityIs(id, CONFIG.getDefaultQuantity()).cartCountIs(0);
        page.setQuantity(id, quantity);
        page.should().quantityIs(id, quantity);
        page.addToCart(id).openCart();
        page.should().cartCountIs(quantity).cartIsOpen(1)
                .cartItemIs(id, name, quantity, total)
                .totalPriceIs(total).totalPriceDoesNotExceed(CONFIG.getOrderLimit());

        page.placeOrder();
        page.should().orderIsProcessed();
    }
}
