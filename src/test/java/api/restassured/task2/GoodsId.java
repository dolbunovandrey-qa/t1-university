package api.restassured.task2;

import com.github.javafaker.Faker;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Tag;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;
import java.util.List;

import static io.restassured.RestAssured.given;
import static org.assertj.core.api.Assertions.assertThat;

@Tag("api")
public class GoodsId extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();
    private Faker faker = new Faker();
    double patchPrice = faker.number().randomDouble(2, 0, 1000);
    String patchProductName =  faker.commerce().productName();
    public record Good(String name, Double price) {}

    @Test
    @DisplayName("Успешный просмотр по id")
    void getGoodsListIdSuccess() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePost.jsonPath().getInt("data.id");
        addForDelete(id);
        Response responseGet = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .log().all()
                .when()
                .get("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responseGet.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        assertThat(responseGet.jsonPath().getInt("id"))
                .as("Id не соответствует запрашиваемому")
                .isEqualTo(id);
    }
    @Test
    @DisplayName("404 Not found при просмотре")
    void getGoodsListIdNotFound() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePost.jsonPath().getInt("data.id")+1;
        addForDelete(responsePost.jsonPath().getInt("data.id"));
        String expectedMessage = "Good with id '"+id+"' is not found!";
        Response responseGet = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .log().all()
                .when()
                .get("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responseGet.statusCode())
                .as("Статус код должен быть 404")
                .isEqualTo(404);
        assertThat(responseGet.jsonPath().getString("message"))
                .as("Ошибка в тексте сообщения")
                .isEqualTo(expectedMessage);
    }

    @Test
    @DisplayName("Успешное изменение")
    void patchGoodsListIdSuccess() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePost.jsonPath().getInt("data.id");
        addForDelete(responsePost.jsonPath().getInt("data.id"));

        Response responsePatch = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .contentType(ContentType.JSON)
                .body(new Good(patchProductName, patchPrice))
                .log().all()
                .when()
                .patch("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responsePatch.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        assertThat(responsePatch.jsonPath().getInt("id"))
                .as("Id не соответствует запрашиваемому")
                .isEqualTo(id);
        assertThat(responsePatch.jsonPath().getString("name"))
                .as("Имя не соответствует ОР")
                .isEqualTo(patchProductName);
        assertThat(responsePatch.jsonPath().getDouble("price"))
                .as("Имя не соответствует ОР")
                .isEqualTo(patchPrice);
    }
    @Test
    @DisplayName("404 Not Found при изменении")
    void patchGoodsListIdNotFound() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePost.jsonPath().getInt("data.id")+1;
        addForDelete(responsePost.jsonPath().getInt("data.id"));

        Response responsePatch = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .contentType(ContentType.JSON)
                .body(new Good(patchProductName, patchPrice))
                .log().all()
                .when()
                .patch("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responsePatch.statusCode())
                .as("Статус код должен быть 404")
                .isEqualTo(404);
        assertThat(responsePatch.body().asString())
                .as("Ошибка в тексте сообщения")
                .isEqualTo("Good with id '"+id+"' is not found");
    }
    @Test
    @DisplayName("Проверка валидации имени при изменении")
    void patchGoodsListIdBadRequest() {
        Response responsePostFirst = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePostFirst.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePostFirst.jsonPath().getInt("data.id");
        addForDelete(responsePostFirst.jsonPath().getInt("data.id"));

        Response responsePostSecond = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(patchProductName, patchPrice))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        addForDelete(responsePostSecond.jsonPath().getInt("data.id"));
        assertThat(responsePostSecond.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);

        Response responsePatch = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .contentType(ContentType.JSON)
                .body(new Good(patchProductName, patchPrice))
                .log().all()
                .when()
                .patch("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responsePatch.statusCode())
                .as("Статус код должен быть 400")
                .isEqualTo(400);
        assertThat(responsePatch.body().asString())
                .as("Сообщение не соответствует ОР")
                .isEqualTo("Good with name '" + patchProductName + "' already exists!");
    }
    @Test
    @DisplayName("Успешное удаление")
    void deleteGoodsListIdSuccess() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePost.jsonPath().getInt("data.id");

        Response responsePatch = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .log().all()
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responsePatch.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        assertThat(responsePatch.body().asString())
                .as("Ошибка в тексте сообщения")
                .isEqualTo("Good with id '"+id+"' has been deleted successfully!");
    }
    @Test
    @DisplayName("404 Not Found при удалении")
    void deleteGoodsListIdNotFound() {
        Response responsePost = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .log().all()
                .contentType(ContentType.JSON)
                .body(new Good(name, price))
                .when()
                .post("/goods/add")
                .then()
                .log().all()
                .extract().response();
        assertThat(responsePost.statusCode())
                .as("Статус код должен быть 200")
                .isEqualTo(200);
        int id = responsePost.jsonPath().getInt("data.id")+1;
        addForDelete(responsePost.jsonPath().getInt("data.id"));

        Response responsePatch = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(),
                        CONFIG.getAdminPassword())
                .pathParam("id",id)
                .log().all()
                .when()
                .delete("/goods/{id}")
                .then()
                .log().all().extract().response();
        assertThat(responsePatch.statusCode())
                .as("Статус код должен быть 404")
                .isEqualTo(404);
        assertThat(responsePatch.body().asString())
                .as("Ошибка в тексте сообщения")
                .isEqualTo("Good with id '"+id+"' is not found");
    }
}
