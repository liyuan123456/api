package com.li.api.repository;

import com.li.api.pojo.model.Activity;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/30 18:56
 */
@Repository
public interface ActivityRepository extends JpaRepository<Activity, Long> {
    Optional<Activity> findByName(String name);

    Optional<Activity> findByCouponListId(Long cid);
}
