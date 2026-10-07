package ui.pageobject;

import io.qameta.allure.Step;

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

    @Step("Проверить загрузку страницы и видимость элементов")
    public MainPageAssert isLoaded() {
        isVisible(page.title, page.productsList, page.adminLink, page.openCartButton, page.cartCount);
        page.title.shouldHave(exactText("🛍 SmartShop"));
        page.products.shouldHave(sizeGreaterThan(0));
        return this;
    }
    @Step("Проверить карточку товара #{id}: {name}, цена {price}")
    public MainPageAssert productIsVisible(int id, String name, BigDecimal price) {
        isVisible(page.product(id), page.quantity(id), page.addButton(id),
                page.quantityButton(id, -1), page.quantityButton(id, 1));
        page.product(id).$("h4").shouldBe(visible).shouldHave(exactText(name));
        hasAmount(page.product(id).$("h4 + div"), price);
        return this;
    }
    @Step("Проверить количество товара #{id}: {expected}")
    public MainPageAssert quantityIs(int id, int expected) {
        hasValue(page.quantity(id), Integer.toString(expected));
        return this;
    }
    @Step("Проверить счётчик корзины: {expected}")
    public MainPageAssert cartCountIs(int expected) {
        page.cartCount.shouldBe(visible).shouldHave(exactText(Integer.toString(expected)));
        return this;
    }
    @Step("Проверить открытую корзину и число разных товаров: {distinctProducts}")
    public MainPageAssert cartIsOpen(int distinctProducts) {
        isVisible(page.cartModal, page.totalPrice, page.orderButton, page.closeCartButton);
        page.cartItems.shouldHave(size(distinctProducts));
        return this;
    }
    @Step("Проверить товар #{id} в корзине: {name}, количество {quantity}, сумма {total}")
    public MainPageAssert cartItemIs(int id, String name, int quantity, BigDecimal total) {
        page.cartItem(id).shouldBe(visible);
        page.cartItem(id).$("b").shouldHave(exactText(name));
        page.cartItem(id).$(".qty-controls span").shouldHave(exactText(Integer.toString(quantity)));
        hasAmount(page.cartItem(id).$("div:nth-child(3)"), total);
        return this;
    }
    @Step("Проверить итоговую сумму корзины: {expected}")
    public MainPageAssert totalPriceIs(BigDecimal expected) {
        hasAmount(page.totalPrice, expected);
        return this;
    }
    @Step("Проверить лимит стоимости заказа: {limit}")
    public MainPageAssert totalPriceDoesNotExceed(BigDecimal limit) {
        page.totalPrice.shouldBe(visible);
        assertThat(amount(page.totalPrice)).as("Стоимость заказа должна быть положительной и не превышать лимит")
                .isPositive().isLessThanOrEqualTo(limit);
        return this;
    }
    @Step("Проверить закрытие корзины")
    public MainPageAssert cartIsClosed() { page.cartModal.shouldBe(hidden); return this; }
    @io.qameta.allure.Step("Проверить уведомление: {expected}")
    public MainPageAssert notificationIs(String expected) {
        page.notifications.findBy(exactText(expected)).shouldBe(visible);
        return this;
    }
    @io.qameta.allure.Step("Проверить, что корзина содержит товары")
    public MainPageAssert cartCountIsNotZero() {
        page.cartCount.shouldBe(visible).shouldNotHave(exactText("0"));
        return this;
    }
    @io.qameta.allure.Step("Проверить отсутствие товара #{id} и пустую корзину")
    public MainPageAssert cartIsEmptyAfterRemoving(int id) {
        page.cartItem(id).shouldNotBe(com.codeborne.selenide.Condition.exist);
        page.cartItems.shouldHave(size(0));
        page.cartModal.$("#empty-cart").shouldBe(visible).shouldHave(exactText("Пусто"));
        cartCountIs(0);
        return this;
    }
    @io.qameta.allure.Step("Проверить JS Alert о превышении лимита 300 рублей")
    public MainPageAssert orderLimitAlertIsShown() {
        org.openqa.selenium.Alert alert = com.codeborne.selenide.Selenide.switchTo().alert();
        String text = alert.getText();
        alert.accept();
        assertThat(text).as("Сообщение о лимите заказа").contains("превышает лимит 300 ₽");
        return this;
    }
    @Step("Проверить обработку заказа и очистку корзины")
    public MainPageAssert orderIsProcessed() {
        page.notifications.findBy(exactText("Заказ принят в обработку!")).shouldBe(visible);
        page.title.shouldBe(visible).shouldHave(exactText("Заказ успешно оформлен!"));
        cartIsClosed();
        cartCountIs(0);
        page.cartItems.shouldHave(size(0));
        return this;
    }
}
