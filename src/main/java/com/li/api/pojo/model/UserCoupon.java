package com.li.api.pojo.model;

import lombok.*;

import javax.persistence.*;
import java.util.Date;

/**
 * @author 黎源
 * @date 2020/12/30 16:31
 */
@Entity
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
public class UserCoupon {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private Long userId;
    private Long couponId;
    private Integer status;
    private Date createTime;
    private Long orderId;
    private Date updateTime;

}
