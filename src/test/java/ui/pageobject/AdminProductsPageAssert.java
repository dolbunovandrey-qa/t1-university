package ui.pageobject;

import java.math.BigDecimal;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;

public class AdminProductsPageAssert extends PageAssert {
    private final AdminProductsPage page;
    public AdminProductsPageAssert(AdminProductsPage page) { this.page = page; }
    public AdminProductsPageAssert isLoaded() {
        isVisible(page.newName, page.newPrice, page.addButton, page.catalogLink);
        page.productsTable.shouldBe(exist);
        return this;
    }
    public AdminProductsPageAssert newProductFieldsAre(String name, BigDecimal price) {
        hasValue(page.newName, name);
        hasValue(page.newPrice, price.toPlainString());
        return this;
    }
    public AdminProductsPageAssert productIs(int id, String name, BigDecimal price) {
        hasValue(page.productName(id), name);
        hasValue(page.productPrice(id), price.toPlainString());
        isVisible(page.saveButton(id));
        return this;
    }
    public AdminProductsPageAssert productIsAdded() {
        page.notifications.findBy(exactText("Товар успешно добавлен!")).shouldBe(visible);
        hasValue(page.newName, "");
        hasValue(page.newPrice, "");
        return this;
    }
    public AdminProductsPageAssert productIsSaved(int id) {
        page.notifications.findBy(exactText("Товар #" + id + " обновлен")).shouldBe(visible);
        return this;
    }
}
