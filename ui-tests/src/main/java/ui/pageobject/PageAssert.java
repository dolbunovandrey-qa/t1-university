package ui.pageobject;

import io.qameta.allure.Step;

import com.codeborne.selenide.SelenideElement;
import java.math.BigDecimal;
import config.ConfigReader;
import config.TestConfig;
import utils.Money;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.visible;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/** Общие проверки элементов; UI-проверки находятся в наследниках PageAssert. */
public abstract class PageAssert {
    protected final TestConfig CONFIG = ConfigReader.load();
    @Step("Проверить видимость элементов")
    protected void isVisible(SelenideElement... elements) {
        for (SelenideElement element : elements) element.shouldBe(visible);
    }

    @Step("Проверить точное значение поля")
    protected void hasValue(SelenideElement element, String expected) {
        element.shouldBe(visible).shouldHave(exactValue(expected));
    }

    @Step("Проверить сумму: {expected}")
    protected void hasAmount(SelenideElement element, BigDecimal expected) {
        element.shouldBe(visible);
        assertThat(amount(element)).as("Сумма в элементе %s", element)
                .isCloseTo(expected, within(CONFIG.getMoneyTolerance()));
    }

    @Step("Прочитать сумму из элемента")
    protected BigDecimal amount(SelenideElement element) {
        return Money.parse(element.getText());
    }
}
