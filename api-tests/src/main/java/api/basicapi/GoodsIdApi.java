package api.basicapi;

import api.constants.ApiEndpoints;
import api.dto.Good;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.math.BigDecimal;

public class GoodsIdApi extends BasicApi {
    @Step("GET: получить товар #{id}")
    public Response get(int id) {
        return builder.build().pathParam("id", id).get(ApiEndpoints.GOODS_ID);
    }
    @Step("PATCH: изменить товар #{id}, название «{name}», цена {price}")
    public Response update(int id, String name, BigDecimal price) {
        return builder.build().pathParam("id", id).contentType(ContentType.JSON)
                .body(new Good(name, price)).patch(ApiEndpoints.GOODS_ID);
    }
    @Step("DELETE: удалить товар #{id}")
    public Response delete(int id) {
        return builder.build().pathParam("id", id).delete(ApiEndpoints.GOODS_ID);
    }
}
