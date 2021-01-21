package com.li.api.pojo.bo;

import com.li.api.pojo.dto.SkuInfoDto;
import com.li.api.pojo.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;

/**
 * @author 黎源
 * @date 2021/1/14 21:20
 */
@Getter
@Setter
public class SkuOrderBO {
    private BigDecimal realPrice;
    private Integer count;
    private Long categoryId;

    public SkuOrderBO(Sku sku, SkuInfoDto info){
        realPrice = sku.getRealPrice();
        count = info.getCount();
        categoryId = sku.getCategoryId();
    }

    public BigDecimal getTotalPrice() {
        return realPrice.multiply(new BigDecimal(count));
    }
}
