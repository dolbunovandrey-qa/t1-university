package ui.pageobject;

import com.codeborne.selenide.SelenideElement;
import java.math.BigDecimal;
import static com.codeborne.selenide.Condition.exactValue;
import static com.codeborne.selenide.Condition.visible;
import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.within;

/** Общие проверки элементов; UI-проверки находятся в наследниках PageAssert. */
public abstract class PageAssert {
    protected void isVisible(SelenideElement... elements) {
        for (SelenideElement element : elements) element.shouldBe(visible);
    }

    protected void hasValue(SelenideElement element, String expected) {
        element.shouldBe(visible).shouldHave(exactValue(expected));
    }

    protected void hasAmount(SelenideElement element, BigDecimal expected) {
        element.shouldBe(visible);
        assertThat(amount(element)).as("Сумма в элементе %s", element)
                .isCloseTo(expected, within(new BigDecimal("0.000000001")));
    }

    protected BigDecimal amount(SelenideElement element) {
        return new BigDecimal(element.getText().replace("₽", "")
                .replace("\u00a0", "").replace(" ", "").replace(',', '.').trim());
    }
}
