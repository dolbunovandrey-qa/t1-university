package api.restassured.task1;

import com.github.javafaker.Faker;
import config.BaseTest;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.*;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;


@Tag("api")
public class GoodList extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();

    public record Good(String name, Double price) {
    }

    private RequestSpecification baseGetRS = new RequestSpecBuilder()
            .setBaseUri(CONFIG.getApiUrl())
            .addQueryParam("page", 0)
            .addQueryParam("size", 100)
            .build();

    @Test
    @DisplayName("Метод с использованием инструкций given(), when(), then() ")
    void getGoodsList() {
        given()
                .baseUri(CONFIG.getApiUrl())
                .queryParam("page", 0)
                .queryParam("size", 100)
                .log().all()
                .when()
                .get("goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("goods.size()", equalTo(0));

    }

    @Test
    @DisplayName("Метод с использованием RequestSpecification")
    void getGoodsListRequestSpecification() {
        given()
                .spec(baseGetRS)
                .when()
                .get("goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .contentType(ContentType.JSON)
                .body("goods.size()", equalTo(0));

    }

    @Test
    @DisplayName("Метод, который будет создавать один товар через эндпоинт POST /goods/add и проверять, " +
            "что GET /goods/list вернул его в списке через встроенные проверки REST Assured.")
    void postGoodsAddAndGetGoodsListTest() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("goods/add")
                .then()
                .log().all()
                .extract().response();
        addForDelete(responsePost.jsonPath().getInt("data.id"));

        given()
                .spec(baseGetRS)
                .when()
                .get("goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem(name));
    }

    @Test
    @DisplayName("Метод, который будет создавать один товар через эндпоинт POST /goods/add и проверять," +
            " что GET /goods/list вернул его в списке через AssertJ.")
    void postGoodsAddAndGetGoodsListAssertionTest() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("goods/add")
                .then()
                .log().all()
                .extract().response();
        addForDelete(responsePost.jsonPath().getInt("data.id"));
        Response response = given()
                .spec(baseGetRS)
                .when()
                .get("goods/list")
                .then()
                .extract().response();

        assertThat(response.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        List<String> listName = response.jsonPath().getList("goods.name");
        boolean result = false;
        for (String name : listName) {
            if (name != null && name.equals(name)) {
                result = true;
                break;
            }
        }
        assertThat(result)
                .as("Товар с именем '%s' должен присутствовать в списке", name)
                .isTrue();
    }
}
