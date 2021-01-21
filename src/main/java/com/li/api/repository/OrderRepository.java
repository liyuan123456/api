package com.li.api.repository;

import com.li.api.pojo.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 黎源
 * @date 2021/1/19 15:22
 */
@Repository
public interface OrderRepository extends JpaRepository<Order,Long> {
}
