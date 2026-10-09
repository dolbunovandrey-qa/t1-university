package api.restassured.task2;

import api.assertions.GoodsAssert;
import api.base.ApiBaseTest;
import static constants.HttpStatus.*;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;
import java.math.BigDecimal;

@Tag("api")
public class GoodsList extends ApiBaseTest {
    @Test
    @DisplayName("Успешный запрос списка с созданным товаром")
    @Tag("smoke")
    void getGoodsList() {
        String name = uniqueName(CONFIG.getStartProductName());
        createProduct(name, CONFIG.getStartProductPrice());
        GoodsAssert.listContains(listApi.list(CONFIG.getListPage(), CONFIG.getListSize()), name);
    }
}
