package ui.pageobject;

import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import java.math.BigDecimal;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;

public class AdminProductsPage {
    final SelenideElement newName = $("#n-name");
    final SelenideElement newPrice = $("#n-price");
    final SelenideElement addButton = $("#add-btn");
    final SelenideElement productsTable = $("#tbody");
    final SelenideElement catalogLink = $("a[href='/']");
    final ElementsCollection notifications = $$("#toast-container .toast");

    SelenideElement productName(int id) { return $("#nm-" + id); }
    SelenideElement productPrice(int id) { return $("#pr-" + id); }
    SelenideElement saveButton(int id) { return $("[data-action='update'][data-id='" + id + "']"); }

    public AdminProductsPage enterNewName(String name) { newName.setValue(name); return this; }
    public AdminProductsPage enterNewPrice(BigDecimal price) { newPrice.setValue(price.toPlainString()); return this; }
    public AdminProductsPage addProduct() { addButton.click(); return this; }
    public AdminProductsPage editName(int id, String name) { productName(id).setValue(name); return this; }
    public AdminProductsPage editPrice(int id, BigDecimal price) { productPrice(id).setValue(price.toPlainString()); return this; }
    public AdminProductsPage saveProduct(int id) { saveButton(id).click(); return this; }
    public MainPage returnToCatalog() {
        catalogLink.click();
        return new MainPage();
    }
    public AdminProductsPageAssert should() { return new AdminProductsPageAssert(this); }
}
