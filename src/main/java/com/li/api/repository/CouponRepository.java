package com.li.api.repository;

import com.li.api.pojo.model.Coupon;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/31 15:05
 */
@Repository
public interface CouponRepository extends JpaRepository<Coupon, Long> {
    @Query(nativeQuery = true,
            value = "SELECT c.* FROM coupon c " +
                    "LEFT JOIN coupon_category cc ON c.id = cc.coupon_id " +
                    "LEFT JOIN category cg ON cc.category_id = cg.id " +
                    "LEFT JOIN activity a ON c.activity_id = a.id " +
                    "WHERE cc.category_id = :cid " +
                    "AND a.start_time < :now " +
                    "AND a.end_time > :now")
    Optional<List<Coupon>> findByCategoryId(@Param(value = "cid") Long cid, @Param("now") Date now);

    @Query(nativeQuery = true,
            value = "SELECT c.* FROM coupon c " +
                    "LEFT JOIN activity a ON c.activity_id = a.id " +
                    "WHERE c.whole_store = :is " +
                    "AND a.start_time < :now " +
                    "AND a.end_time > :now")
    Optional<List<Coupon>> findByWholeStore(@Param(value = "is") Boolean wholeStore, @Param("now") Date now);

    @Query(nativeQuery = true,value = "Select c.* from coupon c " +
            "LEFT JOIN user_coupon us " +
            "ON c.id = us.coupon_id " +
            "WHERE us.user_id = :uid " +
            "AND us.`status` = 1 " +
            "AND us.order_id IS NULL " +
            "AND c.start_time < :now " +
            "AND c.end_time > :now")
    Optional<List<Coupon>> findMineByUsableStatus(@Param(value = "uid") Long uid,
                                                  @Param(value = "now") Date now);

    @Query(nativeQuery = true,value = "Select c.* from coupon c " +
            "LEFT JOIN user_coupon us " +
            "ON c.id = us.coupon_id " +
            "WHERE us.user_id = :uid " +
            "AND us.`status` = 2 " +
            "AND us.order_id IS NOT NULL " +
            "AND c.start_time < :now " +
            "AND c.end_time > :now")
    Optional<List<Coupon>> findMineByUsedStatus(@Param(value = "uid") Long uid,
                                                @Param(value = "now") Date now);

    @Query(nativeQuery = true,value = "Select c.* from coupon c " +
            "LEFT JOIN user_coupon us " +
            "ON c.id = us.coupon_id " +
            "WHERE us.user_id = :uid " +
            "AND us.`status` <> 2 " +
            "AND us.order_id IS NULL " +
            "AND c.end_time < :now")
    Optional<List<Coupon>> findMineByOverdueStatus(@Param(value = "uid") Long uid,
                                                @Param(value = "now") Date now);
}
