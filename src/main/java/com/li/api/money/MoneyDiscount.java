package com.li.api.money;

import java.math.BigDecimal;

/**
 * @author 黎源
 * @date 2021/1/13 20:35
 */
public interface MoneyDiscount {
    BigDecimal mathDiscount(BigDecimal original, BigDecimal discout);
}
