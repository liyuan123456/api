package com.li.api.repository;

import com.li.api.pojo.model.Order;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/19 15:22
 */
@Repository
public interface OrderRepository extends JpaRepository<Order, Long> {
    Page<Order> findByExpiredTimeGreaterThanAndStatusAndUserId(Date date,
                                                        Integer status,
                                                        Long uid,
                                                        Pageable pageable);

    Page<Order> findAllByUserId(Long uid, Pageable pageable);

    Page<Order> findByUserIdAndStatus(Long uid, Integer status, Pageable pageable);

    Optional<Order> findByUserIdAndId(Long uid, Long id);
}
