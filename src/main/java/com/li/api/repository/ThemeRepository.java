package com.li.api.repository;

import com.li.api.pojo.model.Theme;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/7 19:53
 */

@Repository
public interface ThemeRepository extends JpaRepository<Theme, Long> {
    @Query("select t from Theme t where name in (:names)")
    List<Theme> getThemesByNames(@Param("names") List<String> names);

    Optional<Theme> findByName(String name);
}
