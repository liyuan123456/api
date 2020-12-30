package com.li.api.repository;

import com.li.api.pojo.model.GridCategory;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

/**
 * @author 黎源
 * @date 2020/12/7 17:36
 */
@Repository
public interface GridCategoryRepository extends JpaRepository<GridCategory,Long> {
}
