package com.li.api.money;

import com.li.api.exception.ParamterException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 黎源
 * @date 2021/1/13 20:39
 */
@Component
public class HalfUpDiscount implements MoneyDiscount {
    @Override
    public BigDecimal mathDiscount(BigDecimal original, BigDecimal discout) {
        BigDecimal finalMoney;
        try {
            finalMoney = original.multiply(discout).setScale(2, RoundingMode.HALF_UP);
        } catch (Exception e) {
            throw new ParamterException(10001);
        }
        return finalMoney;
    }
}
