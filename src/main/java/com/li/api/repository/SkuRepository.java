package com.li.api.repository;

import com.li.api.pojo.model.Sku;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/11 16:16
 */
@Repository
public interface SkuRepository extends JpaRepository<Sku, Long> {
    Optional<List<Sku>> findAllByIdIn(List<Long> ids);

    @Modifying
    @Transactional
    @Query(nativeQuery = true,
    value = "UPDATE sku s SET s.stock = s.stock - :quantity " +
            "WHERE s.id = :sid " +
            "AND s.stock >= :quantity ")
    int reduceStock(@Param(value = "sid") Long sid,@Param(value = "quantity") Long quantity);
}
