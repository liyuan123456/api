package com.li.api.repository;

import com.li.api.pojo.model.Spu;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 黎源
 * @date 2020/11/29 20:33
 */
@Repository
public interface SpuRepository extends JpaRepository<Spu, Long> {
    Spu findOneById(Long id);

    Page<Spu> findAllByRootCategoryId(Long cid, Pageable pageable);

    Page<Spu> findAllByCategoryId(Long cid, Pageable pageable);
}
