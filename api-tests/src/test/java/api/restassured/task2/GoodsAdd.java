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
public class GoodsAdd extends ApiBaseTest {
    @Test
    @DisplayName("Успешное создание товара")
    @Tag("smoke")
    void postGoodsAddSuccess() {
        String name = uniqueName(CONFIG.getStartProductName());
        Response response = addApi.add(name, CONFIG.getStartProductPrice());
        addForDelete(GoodsAssert.createdProductId(response));
    }

    @Test
    @DisplayName("Проверка уникальности имени при создании товара")
    void postGoodsAddBadRequest() {
        String name = uniqueName(CONFIG.getStartProductName());
        BigDecimal price = CONFIG.getStartProductPrice();
        createProduct(name, price);
        Response duplicate = addApi.add(name, price);
        GoodsAssert.statusIs(duplicate, BAD_REQUEST);
        GoodsAssert.messageIs(duplicate, CONFIG.getApiDuplicateMessageTemplate().formatted(name));
    }
}
