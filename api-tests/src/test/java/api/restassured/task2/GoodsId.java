package api.restassured.task2;

import api.assertions.GoodsAssert;
import api.base.ApiBaseTest;
import static constants.HttpStatus.*;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodsId extends ApiBaseTest {
    @Test
    @DisplayName("Успешный просмотр товара по ID")
    @Tag("smoke")
    void getGoodsListIdSuccess() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        int id = createProduct(name, price);
        GoodsAssert.productIs(idApi.get(id), id, name, price);
    }

    @Test
    @DisplayName("404 при просмотре отсутствующего товара")
    void getGoodsListIdNotFound() {
        int id = createAbsentProductId();
        Response response = idApi.get(id);
        GoodsAssert.statusIs(response, NOT_FOUND);
        GoodsAssert.messageIs(response, CONFIG.getApiNotFoundMessageTemplate().formatted(id));
    }

    @Test
    @DisplayName("Успешное изменение названия и цены товара")
    @Tag("smoke")
    void patchGoodsListIdSuccess() {
        int id = createProduct(uniqueName(CONFIG.getStartProductName()), CONFIG.getStartProductPrice());
        String updatedName = uniqueName(CONFIG.getUpdatedProductNamePrefix());
        BigDecimal updatedPrice = CONFIG.getUpdatedProductPrice();
        GoodsAssert.productIs(idApi.update(id, updatedName, updatedPrice), id, updatedName, updatedPrice);
        GoodsAssert.productIs(idApi.get(id), id, updatedName, updatedPrice);
    }

    @Test
    @DisplayName("404 при изменении отсутствующего товара")
    void patchGoodsListIdNotFound() {
        int id = createAbsentProductId();
        Response response = idApi.update(id, uniqueName(CONFIG.getUpdatedProductNamePrefix()), CONFIG.getUpdatedProductPrice());
        GoodsAssert.statusIs(response, NOT_FOUND);
        GoodsAssert.bodyIs(response, CONFIG.getApiNotFoundBodyTemplate().formatted(id));
    }

    @Test
    @DisplayName("Проверка уникальности имени при изменении")
    void patchGoodsListIdBadRequest() {
        BigDecimal price = CONFIG.getStartProductPrice();
        int firstId = createProduct(uniqueName(CONFIG.getStartProductName()), price);
        String secondName = uniqueName(CONFIG.getStartProductName());
        createProduct(secondName, price);
        Response response = idApi.update(firstId, secondName, price);
        GoodsAssert.statusIs(response, BAD_REQUEST);
        GoodsAssert.bodyIs(response, CONFIG.getApiDuplicateMessageTemplate().formatted(secondName));
    }

    @Test
    @DisplayName("Успешное удаление товара")
    @Tag("smoke")
    void deleteGoodsListIdSuccess() {
        int id = createProduct(uniqueName(CONFIG.getStartProductName()), CONFIG.getStartProductPrice());
        Response response = idApi.delete(id);
        GoodsAssert.statusIs(response, OK);
        GoodsAssert.bodyIs(response, CONFIG.getApiDeletedBodyTemplate().formatted(id));
        GoodsAssert.statusIs(idApi.get(id), NOT_FOUND);
    }

    @Test
    @DisplayName("404 при удалении отсутствующего товара")
    void deleteGoodsListIdNotFound() {
        int id = createAbsentProductId();
        Response response = idApi.delete(id);
        GoodsAssert.statusIs(response, NOT_FOUND);
        GoodsAssert.bodyIs(response, CONFIG.getApiNotFoundBodyTemplate().formatted(id));
    }

    @io.qameta.allure.Step("Подготовить ID гарантированно отсутствующего товара")
    private int createAbsentProductId() {
        int id = createProduct(uniqueName(CONFIG.getStartProductName()), CONFIG.getStartProductPrice());
        GoodsAssert.statusIs(idApi.delete(id), OK);
        return id;
    }
}
