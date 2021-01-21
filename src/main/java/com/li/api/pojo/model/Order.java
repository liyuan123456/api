package com.li.api.pojo.model;

import com.fasterxml.jackson.core.type.TypeReference;
import com.li.api.pojo.dto.OrderAddressDto;
import com.li.api.util.GenericAndJson;
import lombok.*;
import org.hibernate.annotations.Where;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @author 黎源
 * @date 2021/1/11 14:29
 */
@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Where(clause = "delete_time is null")
@Table(name = "`Order`")
public class Order extends BaseEntity {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String orderNo;
    private Long userId;
    private BigDecimal totalPrice;
    private Long totalCount;
    private Date expiredTime;
    private Date placedTime;
    private String snapImg;
    private String snapTitle;
    private String snapItems;
    private String snapAddress;
    private String prepayId;
    private BigDecimal finalTotalPrice;
    private Integer status;

    public void setSnapAddress(OrderAddressDto snapAddress) {
        this.snapAddress = GenericAndJson.ObjectToJson(snapAddress);
    }

    public OrderAddressDto getSnapAddress() {
        if (snapAddress == null) {
            return null;
        }
        return GenericAndJson.JsonToObject(snapAddress, new TypeReference<OrderAddressDto>(){});
    }

    public List<OrderSku> getSnapItems() {
        if (snapItems == null) {
            return new ArrayList<>();
        }
        return GenericAndJson.JsonToObject(snapItems, new TypeReference<List<OrderSku>>() {});
    }

    public void setSnapItems(List<OrderSku> orderSkus) {
        if (orderSkus.isEmpty()) {
            return;
        }
        this.snapItems = GenericAndJson.ObjectToJson(orderSkus);
    }
}
