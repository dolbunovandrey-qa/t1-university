package api.dto;

import java.math.BigDecimal;

/** Тело запроса создания или изменения товара. */
public record Good(String name, BigDecimal price) { }
