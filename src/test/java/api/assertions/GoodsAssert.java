package api.assertions;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.hasItem;

public class GoodsAssert {
    @Step("Проверить удаление тестового товара или его отсутствие")
    public static void deletedOrAlreadyAbsent(Response response) {
        assertThat(response.statusCode()).as("Статус удаления тестовых данных").isIn(200, 404);
    }

    @Step("Проверить HTTP-статус: {expected}")
    public static void statusIs(Response response, int expected) {
        assertThat(response.statusCode()).as("HTTP-статус").isEqualTo(expected);
    }

    @Step("Проверить успешное создание товара и положительный ID")
    public static int createdProductId(Response response) {
        statusIs(response, 200);
        messageIs(response, "success");
        int id = response.jsonPath().getInt("data.id");
        assertThat(id).as("ID созданного товара").isPositive();
        return id;
    }

    @Step("Проверить сообщение API: {expected}")
    public static void messageIs(Response response, String expected) {
        assertThat(response.jsonPath().getString("message")).as("Сообщение API").isEqualTo(expected);
    }

    @Step("Проверить текст тела ответа: {expected}")
    public static void bodyIs(Response response, String expected) {
        assertThat(response.asString()).as("Тело ответа").isEqualTo(expected);
    }

    @Step("Проверить ID товара: {expected}")
    public static void idIs(Response response, int expected) {
        assertThat(response.jsonPath().getInt("id")).as("ID товара").isEqualTo(expected);
    }

    @Step("Проверить название и цену товара: «{name}», {price}")
    public static void productIs(Response response, int id, String name, BigDecimal price) {
        statusIs(response, 200);
        idIs(response, id);
        assertThat(response.jsonPath().getString("name")).as("Название товара").isEqualTo(name);
        assertThat(new BigDecimal(response.jsonPath().getString("price")))
                .as("Цена товара").isEqualByComparingTo(price);
    }

    @Step("Проверить пустой JSON-список товаров встроенными проверками RestAssured")
    public static void listIsEmpty(Response response) {
        response.then().statusCode(200).contentType(ContentType.JSON).body("goods.size()", equalTo(0));
    }

    @Step("Проверить наличие товара «{name}» встроенной проверкой RestAssured")
    public static void listContainsUsingRestAssured(Response response, String name) {
        response.then().statusCode(200).contentType(ContentType.JSON).body("goods.name", hasItem(name));
    }

    @Step("Проверить наличие товара «{name}» в списке через AssertJ")
    public static void listContains(Response response, String name) {
        statusIs(response, 200);
        List<String> names = response.jsonPath().getList("goods.name");
        assertThat(names).as("Названия товаров").contains(name);
    }
}
