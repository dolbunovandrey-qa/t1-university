package api.restassured.task1;

import api.assertions.GoodsAssert;
import api.base.ApiBaseTest;
import static constants.HttpStatus.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodList extends ApiBaseTest {
    @Test
    @DisplayName("Получить список товаров через basicApi и проверить формат")
    void getGoodsList() {
        GoodsAssert.listIsValid(listApi.list(CONFIG.getListPage(), CONFIG.getListSize()), CONFIG.getListSize());
    }

    @Test
    @DisplayName("Получить список товаров и проверить размер страницы")
    void getGoodsListRequestSpecification() {
        GoodsAssert.listIsValid(listApi.list(CONFIG.getListPage(), CONFIG.getSmallListSize()), CONFIG.getSmallListSize());
    }

    @Test
    @DisplayName("Создать товар и проверить его наличие через RestAssured")
    void postGoodsAddAndGetGoodsListTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        createProduct(name, CONFIG.getStartProductPrice());
        GoodsAssert.listContainsUsingRestAssured(listApi.list(CONFIG.getListPage(), CONFIG.getListSize()), name);
    }

    @Test
    @DisplayName("Создать товар и проверить его наличие через AssertJ")
    void postGoodsAddAndGetGoodsListAssertionTest() {
        String name = uniqueName(CONFIG.getStartProductName());
        createProduct(name, CONFIG.getStartProductPrice());
        GoodsAssert.listContains(listApi.list(CONFIG.getListPage(), CONFIG.getListSize()), name);
    }
}
