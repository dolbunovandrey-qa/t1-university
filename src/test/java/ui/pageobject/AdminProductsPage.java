package ui.pageobject;

import io.qameta.allure.Step;

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

    @Step("Ввести название нового товара: {name}")
    public AdminProductsPage enterNewName(String name) { newName.setValue(name); return this; }
    @Step("Ввести цену нового товара: {price}")
    public AdminProductsPage enterNewPrice(BigDecimal price) { newPrice.setValue(price.toPlainString()); return this; }
    @Step("Создать товар через UI")
    public AdminProductsPage addProduct() { addButton.click(); return this; }
    @Step("Изменить название товара #{id}: {name}")
    public AdminProductsPage editName(int id, String name) { productName(id).setValue(name); return this; }
    @Step("Изменить цену товара #{id}: {price}")
    public AdminProductsPage editPrice(int id, BigDecimal price) { productPrice(id).setValue(price.toPlainString()); return this; }
    @Step("Сохранить товар #{id}")
    public AdminProductsPage saveProduct(int id) { saveButton(id).click(); return this; }
    @Step("Вернуться к каталогу")
    public MainPage returnToCatalog() {
        catalogLink.click();
        return new MainPage();
    }
    public AdminProductsPageAssert should() { return new AdminProductsPageAssert(this); }
    @Step("Получить ID товара «{name}» из таблицы админки")
    public int productId(String name) {
        SelenideElement row = productsTable.$$("tr").findBy(
                com.codeborne.selenide.Condition.match("название товара: " + name,
                        element -> name.equals(element.findElement(org.openqa.selenium.By.cssSelector("input[type='text']"))
                                .getAttribute("value"))));
        return Integer.parseInt(row.$("td").getText());
    }
}
