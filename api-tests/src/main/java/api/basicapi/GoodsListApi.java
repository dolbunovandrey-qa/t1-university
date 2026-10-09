package api.basicapi;

import api.constants.ApiEndpoints;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class GoodsListApi extends BasicApi {
    @Step("GET: список товаров, страница {page}, размер {size}")
    public Response list(int page, int size) {
        return builder.build().queryParam("page", page).queryParam("size", size).get(ApiEndpoints.GOODS_LIST);
    }
}
