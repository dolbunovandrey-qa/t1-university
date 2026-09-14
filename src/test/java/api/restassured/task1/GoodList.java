package api.restassured.task1;

import com.github.javafaker.Faker;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;
import static org.hamcrest.Matchers.*;
import static org.hamcrest.Matchers.hasItem;


@Tag("api")
public class GoodList {
    private Faker faker = new Faker();
    String productName = faker.commerce().productName();
    double price = faker.number().randomDouble(2, 0, 1000);

    public record Good(String name, Double price) {
    }

    private RequestSpecification baseGetRS = new RequestSpecBuilder()
            .setBaseUri("http://localhost:8080")
            .addQueryParam("page", 0)
            .addQueryParam("size", 100)
            .build();

    @Test
    @DisplayName("Метод с использованием инструкций given(), when(), then() ")
    void getGoodsList() {
        given()
                .baseUri("http://localhost:8080")
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
        given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(productName, price))
                .when()
                .post("goods/add")
                .then()
                .log().all();
        given()
                .spec(baseGetRS)
                .when()
                .get("goods/list")
                .then()
                .log().all()
                .statusCode(200)
                .body("goods.name", hasItem(productName));
    }

    @Test
    @DisplayName("Метод, который будет создавать один товар через эндпоинт POST /goods/add и проверять," +
            " что GET /goods/list вернул его в списке через AssertJ.")
    void postGoodsAddAndGetGoodsListAssertionTest() {
        given()
                .baseUri("http://localhost:8080")
                .auth().basic("admin", "secret123")
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(productName, price))
                .when()
                .post("goods/add")
                .then()
                .log().all();
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
            if (name != null && name.equals(productName)) {
                result = true;
                break;
            }
        }
        assertThat(result)
                .as("Товар с именем '%s' должен присутствовать в списке", productName)
                .isTrue();


    }
}
