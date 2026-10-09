package api.steps;

import api.assertions.GoodsAssert;
import api.basicapi.GoodsAddApi;
import api.basicapi.GoodsIdApi;
import api.basicapi.GoodsListApi;
import config.ConfigReader;
import config.TestConfig;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import java.math.BigDecimal;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import static constants.HttpStatus.OK;

/** Подготовка и уборка данных общие для API- и UI-тестов. */
public class ProductSteps {
    private final TestConfig config = ConfigReader.load();
    private final GoodsAddApi addApi = new GoodsAddApi();
    private final GoodsIdApi idApi = new GoodsIdApi();
    private final GoodsListApi listApi = new GoodsListApi();
    private final Set<Integer> ids = new LinkedHashSet<>();
    private final Set<String> uiNames = new LinkedHashSet<>();

    @Step("Создать тестовый товар «{name}» и зарегистрировать для удаления")
    public int createProduct(String name, BigDecimal price) {
        int id = GoodsAssert.createdProductId(addApi.add(name, price));
        addForDelete(id);
        return id;
    }
    @Step("Зарегистрировать товар #{id} для удаления")
    public void addForDelete(int id) { ids.add(id); }
    @Step("Зарегистрировать создаваемый через UI товар «{name}» для удаления")
    public void trackUiProduct(String name) { uiNames.add(name); }

    @Step("Найти созданные через UI товары")
    private void collectUiProducts() {
        for (String name : uiNames) {
            for (int page = config.getListPage(); ; page++) {
                Response response = listApi.list(page, config.getListSize());
                GoodsAssert.statusIs(response, OK);
                List<Map<String, Object>> products = response.jsonPath().getList("goods");
                boolean found = false;
                for (Map<String, Object> product : products) {
                    if (name.equals(product.get("name"))) {
                        addForDelete(((Number) product.get("id")).intValue());
                        found = true;
                        break;
                    }
                }
                if (found || products.size() < config.getListSize()) break;
            }
        }
    }
    @Step("Удалить созданные тестом товары")
    public void cleanup() {
        collectUiProducts();
        for (int id : ids) GoodsAssert.deletedOrAlreadyAbsent(idApi.delete(id));
        ids.clear();
        uiNames.clear();
    }
}
