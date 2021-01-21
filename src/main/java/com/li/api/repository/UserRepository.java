package com.li.api.repository;

import com.li.api.pojo.model.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/22 16:29
 */
@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findUserByOpenid(String openid);

}
