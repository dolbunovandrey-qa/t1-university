package ui.pageobject;

import io.qameta.allure.Step;

import java.math.BigDecimal;
import static com.codeborne.selenide.Condition.exactText;
import static com.codeborne.selenide.Condition.exist;
import static com.codeborne.selenide.Condition.visible;

public class AdminProductsPageAssert extends PageAssert {
    private final AdminProductsPage page;
    public AdminProductsPageAssert(AdminProductsPage page) { this.page = page; }
    @Step("Проверить загрузку страницы и видимость элементов")
    public AdminProductsPageAssert isLoaded() {
        isVisible(page.newName, page.newPrice, page.addButton, page.catalogLink);
        page.productsTable.shouldBe(exist);
        return this;
    }
    @Step("Проверить поля нового товара: {name}, {price}")
    public AdminProductsPageAssert newProductFieldsAre(String name, BigDecimal price) {
        hasValue(page.newName, name);
        hasValue(page.newPrice, price.toPlainString());
        return this;
    }
    @Step("Проверить поля товара #{id}: {name}, {price}")
    public AdminProductsPageAssert productIs(int id, String name, BigDecimal price) {
        hasValue(page.productName(id), name);
        hasValue(page.productPrice(id), price.toPlainString());
        isVisible(page.saveButton(id));
        return this;
    }
    @Step("Проверить добавление товара и очистку формы")
    public AdminProductsPageAssert productIsAdded() {
        page.notifications.findBy(exactText(CONFIG.getProductAddedNotification())).shouldBe(visible);
        hasValue(page.newName, "");
        hasValue(page.newPrice, "");
        return this;
    }
    @Step("Проверить уведомление о сохранении товара #{id}")
    public AdminProductsPageAssert productIsSaved(int id) {
        page.notifications.findBy(exactText(CONFIG.getProductUpdatedMessageTemplate().formatted(id))).shouldBe(visible);
        return this;
    }
}
