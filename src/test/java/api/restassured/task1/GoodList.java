package api.restassured.task1;

import api.assertions.GoodsAssert;
import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodList extends BaseTest {
    @Test
    @DisplayName("Получить пустой список товаров через given/when/then")
    void getGoodsList() {
        GoodsAssert.listIsEmpty(goods.list(0, 100));
    }

    @Test
    @DisplayName("Получить пустой список товаров через RequestSpecification")
    void getGoodsListRequestSpecification() {
        GoodsAssert.listIsEmpty(goods.listWithSpecification(0, 100));
    }

    @Test
    @DisplayName("Создать товар и проверить его наличие через RestAssured")
    void postGoodsAddAndGetGoodsListTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        createProduct(name, BigDecimal.valueOf(CONFIG.getStartProductPrice()));
        GoodsAssert.listContainsUsingRestAssured(goods.listWithSpecification(0, 100), name);
    }

    @Test
    @DisplayName("Создать товар и проверить его наличие через AssertJ")
    void postGoodsAddAndGetGoodsListAssertionTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        createProduct(name, BigDecimal.valueOf(CONFIG.getStartProductPrice()));
        GoodsAssert.listContains(goods.listWithSpecification(0, 100), name);
    }
}
