package com.li.api.logic;

import com.li.api.exception.ForbiddenException;
import com.li.api.exception.ParamterException;
import com.li.api.pojo.bo.SkuOrderBO;
import com.li.api.pojo.dto.OrderDto;
import com.li.api.pojo.dto.SkuInfoDto;
import com.li.api.pojo.model.OrderSku;
import com.li.api.pojo.model.Sku;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/1/16 14:26
 */
@Getter
@Setter
public class OrderChecker {
    private OrderDto orderDto;
    private List<Sku> serverSkus;
    private Integer maxBuy;
    private CouponChecker couponChecker;

    @Getter
    private List<OrderSku> orderSkus = new ArrayList<>();

    public OrderChecker(OrderDto orderDto, List<Sku> serverSkus, CouponChecker couponChecker,Integer maxBuy) {
        this.orderDto = orderDto;
        this.serverSkus = serverSkus;
        this.couponChecker = couponChecker;
        this.maxBuy = maxBuy;
    }

    public void isOK() {
        BigDecimal serverTotalPrice = new BigDecimal("0");
        List<SkuOrderBO> skuOrderBOS = new ArrayList<>();
        // 2021/1/16 购买商品是否下架
        isOff(orderDto.getSkuInfoDtoList().size(), serverSkus.size());
        //  2021/1/16 购买商品是否售罄、是否超出库存、是否超出最大购买量
        //  orderTotalPrice = serverTotalPrice?
        for (int i = 0; i < serverSkus.size(); i++) {
            Sku serverSku = serverSkus.get(i);
            SkuInfoDto orderSku = orderDto.getSkuInfoDtoList().get(i);
            isSoldOut(serverSku);
            isOverSold(serverSku.getStock(),orderSku.getCount());
            isExmax(orderSku.getCount());
            serverTotalPrice = serverTotalPrice.add(MultiplyForServerTotalPrice(serverSku,orderSku));
            skuOrderBOS.add(new SkuOrderBO(serverSku, orderSku));

            orderSkus.add(new OrderSku(serverSku,orderSku));
        }
        //比较前端未使用优惠券总价和服务端未使用优惠券总价
        PriceCompare(orderDto.getTotalPrice(),serverTotalPrice);
        //优惠券校验
        if(couponChecker != null){
            couponChecker.isOK();
            couponChecker.canBeUsed(skuOrderBOS,serverTotalPrice);
            couponChecker.finalTotalPriceIsOK(orderDto.getFinalTotalPrice(),serverTotalPrice);
        }
    }

    /**
     * 是否下架
     * @param count1
     * @param count2
     */
    private void isOff(int count1, int count2) {
        if (count1 != count2) {
            throw new ForbiddenException(50003);
        }
    }

    /**
     * 是否售罄
     * @param sku
     */
    private void isSoldOut(Sku sku) {
        if (sku.getStock() < 1) {
            throw new ForbiddenException(50004);
        }
    }

    /**
     * 是否超卖
     */
    private void isOverSold(long stock,long count) {
        if(count > stock){
            throw new ForbiddenException(50005);
        }
    }

    /**
     * 是否超过最大购买数量
     * @param count
     */
    private void isExmax(int count) {
        if (count > maxBuy) {
            throw new ForbiddenException(50006);
        }
    }

    /**
     * order 中单个sku 在服务器里的价格
     * @param sku
     * @param skuInfoDto
     * @return
     */
    private BigDecimal MultiplyForServerTotalPrice(Sku sku,SkuInfoDto skuInfoDto) {
        if(skuInfoDto.getCount() < 1){
            throw new ParamterException(50001);
        }
       return sku.getRealPrice().multiply(new BigDecimal(skuInfoDto.getCount().toString()));
    }

    /**
     * 校验orderTotalPrice 和 serverTotalPrice 是否一致
     * @param orderTotalPrice
     * @param serverTotalPrice
     */
    private void PriceCompare(BigDecimal orderTotalPrice,BigDecimal serverTotalPrice) {
        int compare = orderTotalPrice.compareTo(serverTotalPrice);
        if(compare != 0){
            throw new ForbiddenException(50002);
        }
    }

    //以下为组装order对象方法

    public String getLeaderImg() {
        return serverSkus.get(0).getImg();
    }

    public String getLeaderTitle() {
        return serverSkus.get(0).getTitle();
    }

    public Integer getTotalCount() {
        return orderDto.getSkuInfoDtoList().stream()
                .map(SkuInfoDto::getCount)
                .reduce(Integer::sum)
                .orElse(0);
    }
}
