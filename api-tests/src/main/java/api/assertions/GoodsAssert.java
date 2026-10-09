package api.assertions;

import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import java.math.BigDecimal;
import config.ConfigReader;
import static constants.HttpStatus.*;
import java.util.List;
import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.Matchers.hasItem;

public class GoodsAssert extends ApiAssert {
    @Step("Проверить удаление тестового товара или его отсутствие")
    public static void deletedOrAlreadyAbsent(Response response) {
        assertThat(response.statusCode()).as("Статус удаления тестовых данных").isIn(OK, NOT_FOUND);
    }


    @Step("Проверить успешное создание товара и положительный ID")
    public static int createdProductId(Response response) {
        statusIs(response, OK);
        messageIs(response, ConfigReader.load().getApiSuccessMessage());
        int id = response.jsonPath().getInt("data.id");
        assertThat(id).as("ID созданного товара").isPositive();
        return id;
    }



    @Step("Проверить ID товара: {expected}")
    public static void idIs(Response response, int expected) {
        assertThat(response.jsonPath().getInt("id")).as("ID товара").isEqualTo(expected);
    }

    @Step("Проверить название и цену товара: «{name}», {price}")
    public static void productIs(Response response, int id, String name, BigDecimal price) {
        statusIs(response, OK);
        idIs(response, id);
        assertThat(response.jsonPath().getString("name")).as("Название товара").isEqualTo(name);
        assertThat(new BigDecimal(response.jsonPath().getString("price")))
                .as("Цена товара").isEqualByComparingTo(price);
    }

    @Step("Проверить JSON-список товаров и размер страницы: {maxSize}")
    public static void listIsValid(Response response, int maxSize) {
        response.then().statusCode(OK).contentType(ContentType.JSON);
        List<?> products = response.jsonPath().getList("goods");
        assertThat(products).as("Список товаров").isNotNull().hasSizeLessThanOrEqualTo(maxSize);
    }

    @Step("Проверить наличие товара «{name}» встроенной проверкой RestAssured")
    public static void listContainsUsingRestAssured(Response response, String name) {
        response.then().statusCode(OK).contentType(ContentType.JSON).body("goods.name", hasItem(name));
    }

    @Step("Проверить наличие товара «{name}» в списке через AssertJ")
    public static void listContains(Response response, String name) {
        statusIs(response, OK);
        List<String> names = response.jsonPath().getList("goods.name");
        assertThat(names).as("Названия товаров").contains(name);
    }
}
