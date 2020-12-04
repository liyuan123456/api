package com.li.api.repository;

import com.li.api.pojo.model.Banner;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * @author 黎源
 * @date 2020/11/26 16:20
 */
@Repository
public interface BannerRepository extends JpaRepository<Banner,Long>
{
    Banner findOneByName(String name);
}
