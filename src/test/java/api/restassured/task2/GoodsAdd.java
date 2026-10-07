package api.restassured.task2;

import api.assertions.GoodsAssert;
import config.BaseTest;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodsAdd extends BaseTest {
    @Test
    @DisplayName("Успешное создание товара")
    void postGoodsAddSuccess() {
        String name = uniqueName(CONFIG.getStartProductName());
        Response response = goods.add(name, BigDecimal.valueOf(CONFIG.getStartProductPrice()));
        addForDelete(GoodsAssert.createdProductId(response));
    }

    @Test
    @DisplayName("Проверка уникальности имени при создании товара")
    void postGoodsAddBadRequest() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = BigDecimal.valueOf(CONFIG.getStartProductPrice());
        createProduct(name, price);
        Response duplicate = goods.add(name, price);
        GoodsAssert.statusIs(duplicate, 400);
        GoodsAssert.messageIs(duplicate, "Good with name '" + name + "' already exists!");
    }
}
