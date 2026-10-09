package api.base;

import api.basicapi.GoodsAddApi;
import api.basicapi.GoodsIdApi;
import api.basicapi.GoodsListApi;
import api.steps.ProductSteps;
import config.BaseTest;
import io.qameta.allure.Step;
import org.junit.jupiter.api.AfterEach;
import java.math.BigDecimal;

public abstract class ApiBaseTest extends BaseTest {
    protected final GoodsListApi listApi = new GoodsListApi();
    protected final GoodsAddApi addApi = new GoodsAddApi();
    protected final GoodsIdApi idApi = new GoodsIdApi();
    private final ProductSteps products = new ProductSteps();

    @Step("Подготовить товар «{name}»")
    protected int createProduct(String name, BigDecimal price) { return products.createProduct(name, price); }
    @Step("Зарегистрировать товар #{id} для удаления")
    protected void addForDelete(int id) { products.addForDelete(id); }
    @AfterEach
    @Step("Убрать данные API-теста")
    void tearDown() { products.cleanup(); }
}
