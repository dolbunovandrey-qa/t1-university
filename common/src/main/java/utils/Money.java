package utils;

import java.math.BigDecimal;

public class Money {
    private Money() { }

    public static BigDecimal parse(String text) {
        return new BigDecimal(text.replace("₽", "").replace("\u00a0", "")
                .replace(" ", "").replace(',', '.').trim());
    }
}
