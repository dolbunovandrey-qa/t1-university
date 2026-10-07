package api.restassured.task2;

import api.assertions.GoodsAssert;
import config.BaseTest;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodsList extends BaseTest {
    @Test
    @DisplayName("Успешный запрос списка с созданным товаром")
    void getGoodsList() {
        String name = uniqueName(CONFIG.getStartProductName());
        createProduct(name, BigDecimal.valueOf(CONFIG.getStartProductPrice()));
        GoodsAssert.listContains(goods.list(0, 100), name);
    }
}
