package ui.pageobject;

import java.math.BigDecimal;
import static com.codeborne.selenide.CollectionCondition.size;
import static com.codeborne.selenide.CollectionCondition.sizeGreaterThan;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.hidden;
import static com.codeborne.selenide.Condition.visible;
import static org.assertj.core.api.Assertions.assertThat;

public class MainPageAssert extends PageAssert {
    private final MainPage page;
    public MainPageAssert(MainPage page) { this.page = page; }

    public MainPageAssert isLoaded() {
        isVisible(page.title, page.productsList, page.adminLink, page.openCartButton, page.cartCount);
        page.title.shouldHave(exactText("🛍 SmartShop"));
        page.products.shouldHave(sizeGreaterThan(0));
        return this;
    }
    public MainPageAssert productIsVisible(int id, String name, BigDecimal price) {
        isVisible(page.product(id), page.quantity(id), page.addButton(id),
                page.quantityButton(id, -1), page.quantityButton(id, 1));
        page.product(id).$("h4").shouldBe(visible).shouldHave(exactText(name));
        hasAmount(page.product(id).$("h4 + div"), price);
        return this;
    }
    public MainPageAssert quantityIs(int id, int expected) {
        hasValue(page.quantity(id), Integer.toString(expected));
        return this;
    }
    public MainPageAssert cartCountIs(int expected) {
        page.cartCount.shouldBe(visible).shouldHave(exactText(Integer.toString(expected)));
        return this;
    }
    public MainPageAssert cartIsOpen(int distinctProducts) {
        isVisible(page.cartModal, page.totalPrice, page.orderButton, page.closeCartButton);
        page.cartItems.shouldHave(size(distinctProducts));
        return this;
    }
    public MainPageAssert cartItemIs(int id, String name, int quantity, BigDecimal total) {
        page.cartItem(id).shouldBe(visible);
        page.cartItem(id).$("b").shouldHave(exactText(name));
        page.cartItem(id).$(".qty-controls span").shouldHave(exactText(Integer.toString(quantity)));
        hasAmount(page.cartItem(id).$("div:nth-child(3)"), total);
        return this;
    }
    public MainPageAssert totalPriceIs(BigDecimal expected) {
        hasAmount(page.totalPrice, expected);
        return this;
    }
    public MainPageAssert totalPriceDoesNotExceed(BigDecimal limit) {
        page.totalPrice.shouldBe(visible);
        assertThat(amount(page.totalPrice)).as("Стоимость заказа должна быть положительной и не превышать лимит")
                .isPositive().isLessThanOrEqualTo(limit);
        return this;
    }
    public MainPageAssert cartIsClosed() { page.cartModal.shouldBe(hidden); return this; }
    public MainPageAssert orderIsProcessed() {
        page.notifications.findBy(exactText("Заказ принят в обработку!")).shouldBe(visible);
        page.title.shouldBe(visible).shouldHave(exactText("Заказ успешно оформлен!"));
        cartIsClosed();
        cartCountIs(0);
        page.cartItems.shouldHave(size(0));
        return this;
    }
}
