package config;

import io.qameta.allure.Step;
import java.util.UUID;

/** Общая часть тестов не зависит от браузера или HTTP-клиента. */
public abstract class BaseTest {
    protected static final TestConfig CONFIG = ConfigReader.load();

    @Step("Подготовить уникальное имя товара с префиксом «{prefix}»")
    protected String uniqueName(String prefix) {
        return prefix + " " + UUID.randomUUID();
    }
}
