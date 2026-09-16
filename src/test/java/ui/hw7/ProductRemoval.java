package ui.hw7;

import com.codeborne.selenide.Condition;
import com.codeborne.selenide.DragAndDropOptions;
import com.codeborne.selenide.SelenideElement;
import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.open;
import static io.restassured.RestAssured.given;
import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

public class ProductRemoval extends BaseTest {
    private String name = CONFIG.getStartProductName();
    private double price = CONFIG.getStartProductPrice();
    record Good(String name, double price) {};
    int id;

    SelenideElement productCard = $("[data-name='"+name + "'");
    SelenideElement basketBtn = $("#open-cart-btn");
    SelenideElement remove = $("[data-action = 'remove']");

    @BeforeEach
    void setup(){
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
        id = responsePost.jsonPath().getInt("data.id");
        addForDelete(responsePost.jsonPath().getInt("data.id"));
        open("/");
    }
    @Test
    void testDnD(){
        productCard.dragAndDrop(DragAndDropOptions.to(basketBtn));
        basketBtn.click();
        remove.click();
        $("#cart-item-" + id +" b").shouldNotBe(Condition.exist);
        $("#empty-cart").shouldBe(Condition.text("Пусто"));
    }
}
