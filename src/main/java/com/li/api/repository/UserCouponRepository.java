package com.li.api.repository;

import com.li.api.pojo.model.UserCoupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/6 16:43
 */
@Repository
public interface UserCouponRepository extends JpaRepository<UserCoupon, Long> {
    Optional<UserCoupon> findFirstByUserIdAndCouponId(Long uid, Long cid);


    @Modifying
    @Query(nativeQuery = true,
    value = "UPDATE user_coupon uc " +
            "SET uc.status = 2 , uc.order_id = :oid " +
            "WHERE uc.user_id = :uid " +
            "AND uc.coupon_id = :couponId " +
            "AND uc.status =1 " +
            "AND uc.order_id IS NULL")
    int writeOff(@Param(value = "uid") Long uid,
                 @Param(value = "oid") Long oid,
                 @Param(value = "couponId") Long couponId);
}
