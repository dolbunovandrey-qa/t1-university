package utils;

import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvSource;
import java.math.BigDecimal;
import static org.junit.jupiter.api.Assertions.assertEquals;

class MoneyTest {
    @ParameterizedTest
    @CsvSource(delimiter = '|', value = {"99.12 ₽|99.12", "1 234,56 ₽|1234.56", "1\u00a0234,56 ₽|1234.56"})
    void parsesShopPriceWithoutLosingPrecision(String display, String expected) {
        assertEquals(new BigDecimal(expected), Money.parse(display));
    }
}
