package ui.task3;

import config.BaseTest;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.junit.jupiter.api.AfterEach;
import ui.pageobject.AdminLoginPage;
import ui.pageobject.AdminProductsPage;
import ui.pageobject.ProductFixtureAssert;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.UUID;
import static io.restassured.RestAssured.given;

abstract class PageObjectTest extends BaseTest {
    private final List<String> uiCreatedNames = new ArrayList<>();

    protected String uniqueName(String prefix) { return prefix + " " + UUID.randomUUID(); }

    protected int createProduct(String name, BigDecimal price) {
        Response response = given()
                .baseUri(CONFIG.getApiUrl())
                .auth().basic(CONFIG.getAdminUsername(), CONFIG.getAdminPassword())
                .contentType(ContentType.JSON)
                .body(Map.of("name", name, "price", price))
                .post("/goods/add");
        ProductFixtureAssert.productIsCreated(response);
        int id = response.jsonPath().getInt("data.id");
        addForDelete(id);
        return id;
    }

    protected AdminProductsPage signIn(AdminLoginPage loginPage) {
        loginPage.should().isLoaded();
        loginPage.enterUsername(CONFIG.getAdminUsername()).enterPassword(CONFIG.getAdminPassword());
        loginPage.should().usernameIs(CONFIG.getAdminUsername()).passwordIs(CONFIG.getAdminPassword());
        AdminProductsPage adminPage = loginPage.signIn();
        adminPage.should().isLoaded();
        return adminPage;
    }

    protected void trackUiProduct(String name) { uiCreatedNames.add(name); }

    @AfterEach
    void collectUiCreatedProductsForDeletion() {
        // Выполняется до @AfterEach из BaseTest. Имя регистрируется до клика:
        // товар удалится и при падении проверки уведомления.
        for (String name : uiCreatedNames) {
            boolean found = false;
            for (int page = 0; !found; page++) {
                Response response = given()
                        .baseUri(CONFIG.getApiUrl())
                        .auth().basic(CONFIG.getAdminUsername(), CONFIG.getAdminPassword())
                        .queryParam("page", page).queryParam("size", 100)
                        .get("/goods/list");
                ProductFixtureAssert.requestSucceeded(response);
                List<Map<String, Object>> products = response.jsonPath().getList("goods");
                for (Map<String, Object> product : products) {
                    if (name.equals(product.get("name"))) {
                        addForDelete(((Number) product.get("id")).intValue());
                        found = true;
                        break;
                    }
                }
                if (products.size() < 100) break;
            }
        }
    }
}
