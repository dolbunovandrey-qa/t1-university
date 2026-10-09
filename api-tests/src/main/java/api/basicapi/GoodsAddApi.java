package api.basicapi;

import api.constants.ApiEndpoints;
import api.dto.Good;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.math.BigDecimal;

public class GoodsAddApi extends BasicApi {
    @Step("POST: создать товар «{name}», цена {price}")
    public Response add(String name, BigDecimal price) {
        return builder.build().contentType(ContentType.JSON).body(new Good(name, price)).post(ApiEndpoints.GOODS_ADD);
    }
}
