package api.restassured.task2;

import api.assertions.GoodsAssert;
import config.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodsId extends BaseTest {
    @Test
    @DisplayName("Успешный просмотр товара по ID")
    void getGoodsListIdSuccess() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = BigDecimal.valueOf(CONFIG.getStartProductPrice());
        int id = createProduct(name, price);
        GoodsAssert.productIs(goods.get(id), id, name, price);
    }

    @Test
    @DisplayName("404 при просмотре отсутствующего товара")
    void getGoodsListIdNotFound() {
        int id = createAbsentProductId();
        Response response = goods.get(id);
        GoodsAssert.statusIs(response, 404);
        GoodsAssert.messageIs(response, "Good with id '" + id + "' is not found!");
    }

    @Test
    @DisplayName("Успешное изменение названия и цены товара")
    void patchGoodsListIdSuccess() {
        int id = createProduct(uniqueName("Исходный товар"), new BigDecimal("99.12"));
        String updatedName = uniqueName("Изменённый товар");
        BigDecimal updatedPrice = new BigDecimal("89.45");
        GoodsAssert.productIs(goods.update(id, updatedName, updatedPrice), id, updatedName, updatedPrice);
        GoodsAssert.productIs(goods.get(id), id, updatedName, updatedPrice);
    }

    @Test
    @DisplayName("404 при изменении отсутствующего товара")
    void patchGoodsListIdNotFound() {
        int id = createAbsentProductId();
        Response response = goods.update(id, uniqueName("Изменённый товар"), new BigDecimal("89.45"));
        GoodsAssert.statusIs(response, 404);
        GoodsAssert.bodyIs(response, "Good with id '" + id + "' is not found");
    }

    @Test
    @DisplayName("Проверка уникальности имени при изменении")
    void patchGoodsListIdBadRequest() {
        BigDecimal price = new BigDecimal("99.12");
        int firstId = createProduct(uniqueName("Первый товар"), price);
        String secondName = uniqueName("Второй товар");
        createProduct(secondName, price);
        Response response = goods.update(firstId, secondName, price);
        GoodsAssert.statusIs(response, 400);
        GoodsAssert.bodyIs(response, "Good with name '" + secondName + "' already exists!");
    }

    @Test
    @DisplayName("Успешное удаление товара")
    void deleteGoodsListIdSuccess() {
        int id = createProduct(uniqueName("Товар для удаления"), new BigDecimal("99.12"));
        Response response = goods.delete(id);
        GoodsAssert.statusIs(response, 200);
        GoodsAssert.bodyIs(response, "Good with id '" + id + "' has been deleted successfully!");
        GoodsAssert.statusIs(goods.get(id), 404);
    }

    @Test
    @DisplayName("404 при удалении отсутствующего товара")
    void deleteGoodsListIdNotFound() {
        int id = createAbsentProductId();
        Response response = goods.delete(id);
        GoodsAssert.statusIs(response, 404);
        GoodsAssert.bodyIs(response, "Good with id '" + id + "' is not found");
    }

    @io.qameta.allure.Step("Подготовить ID гарантированно отсутствующего товара")
    private int createAbsentProductId() {
        int id = createProduct(uniqueName("Удаляемый товар"), new BigDecimal("99.12"));
        GoodsAssert.statusIs(goods.delete(id), 200);
        return id;
    }
}
