package ui.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideElement;
import static com.codeborne.selenide.Condition.id;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

/** Каталог товаров и расположенная на нём модальная корзина. */
public class MainPage {
    final SelenideElement title = $("#main-title");
    final SelenideElement productsList = $("#products-list");
    final ElementsCollection products = $$("#products-list .product-card");
    final SelenideElement adminLink = $("a[href='/admin']");
    final SelenideElement openCartButton = $("#open-cart-btn");
    final SelenideElement cartCount = $("#cart-count");
    final SelenideElement cartModal = $("#cartModal");
    final ElementsCollection cartItems = $$("#cart-items .cart-item");
    final SelenideElement totalPrice = $("#total-price");
    final SelenideElement closeCartButton = $("#close-modal");
    final SelenideElement orderButton = $("#makeOrder");
    final ElementsCollection notifications = $$("#toast-container .toast");

    public MainPage open() {
        Selenide.open("/");
        return this;
    }

    SelenideElement product(int productId) { return products.findBy(id("card-" + productId)); }
    SelenideElement quantity(int id) { return product(id).$("input[type='number']"); }
    SelenideElement addButton(int id) { return product(id).$("[data-action='add-to-cart']"); }
    SelenideElement quantityButton(int id, int step) {
        return product(id).$("[data-action='qty-change'][data-step='" + step + "']");
    }
    SelenideElement cartItem(int productId) { return cartItems.findBy(id("cart-item-" + productId)); }

    public MainPage setQuantity(int id, int count) {
        quantity(id).setValue(Integer.toString(count));
        return this;
    }
    public MainPage increaseQuantity(int id) { quantityButton(id, 1).click(); return this; }
    public MainPage decreaseQuantity(int id) { quantityButton(id, -1).click(); return this; }
    public MainPage addToCart(int id) { addButton(id).click(); return this; }
    public MainPage openCart() { openCartButton.click(); return this; }
    public MainPage closeCart() { closeCartButton.click(); return this; }
    public MainPage placeOrder() { orderButton.click(); return this; }
    public AdminLoginPage openAdmin() {
        adminLink.click();
        return new AdminLoginPage();
    }
    public MainPageAssert should() { return new MainPageAssert(this); }
}
