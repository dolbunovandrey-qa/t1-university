package api.client;

import config.TestConfig;
import io.qameta.allure.Step;
import io.qameta.allure.restassured.AllureRestAssured;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import java.math.BigDecimal;
import java.util.Map;
import static io.restassured.RestAssured.given;

/** Все HTTP-вызовы магазина, включая подготовку и уборку тестовых данных. */
public class GoodsClient {
    private final TestConfig config;
    public GoodsClient(TestConfig config) { this.config = config; }

    private RequestSpecification request() {
        return given().baseUri(config.getApiUrl())
                .filter(new AllureRestAssured())
                .auth().preemptive().basic(config.getAdminUsername(), config.getAdminPassword());
    }

    @Step("GET /goods/list: страница {page}, размер {size}")
    public Response list(int page, int size) {
        return request().queryParam("page", page).queryParam("size", size).get("/goods/list");
    }

    @Step("GET /goods/list через RequestSpecification: страница {page}, размер {size}")
    public Response listWithSpecification(int page, int size) {
        RequestSpecification specification = new RequestSpecBuilder()
                .addQueryParam("page", page).addQueryParam("size", size).build();
        return request().spec(specification).get("/goods/list");
    }

    @Step("POST /goods/add: товар «{name}», цена {price}")
    public Response add(String name, BigDecimal price) {
        return request().contentType(ContentType.JSON)
                .body(Map.of("name", name, "price", price)).post("/goods/add");
    }

    @Step("GET /goods/{id}: получить товар")
    public Response get(int id) {
        return request().pathParam("id", id).get("/goods/{id}");
    }

    @Step("PATCH /goods/{id}: название «{name}», цена {price}")
    public Response update(int id, String name, BigDecimal price) {
        return request().pathParam("id", id).contentType(ContentType.JSON)
                .body(Map.of("name", name, "price", price)).patch("/goods/{id}");
    }

    @Step("DELETE /goods/{id}: удалить товар")
    public Response delete(int id) {
        return request().pathParam("id", id).delete("/goods/{id}");
    }
}
