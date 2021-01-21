package com.li.api.money;

import com.li.api.exception.ParamterException;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.math.RoundingMode;

/**
 * @author 黎源
 * @date 2021/1/13 20:48
 */
@Component
public class HalfEvenDiscout implements MoneyDiscount {
    @Override
    public BigDecimal mathDiscount(BigDecimal original, BigDecimal discout) {
        BigDecimal finalMoney;
        try {
            finalMoney = original.multiply(discout).setScale(2, RoundingMode.HALF_EVEN);
        } catch (Exception e) {
            throw new ParamterException(10001);
        }
        return finalMoney;
    }
}
