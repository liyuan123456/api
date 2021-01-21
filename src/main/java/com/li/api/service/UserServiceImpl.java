package com.li.api.service;

import com.li.api.pojo.model.User;
import com.li.api.repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.stereotype.Service;

import java.util.Optional;

/**
 * @author 黎源
 * @date 2021/1/2 15:53
 */

@Service
public class UserServiceImpl {

    @Autowired
    private UserRepository repository;

    public Optional<User> getUserById(Long uid) {
        Optional<User> userOptional = repository.findById(uid);
        return userOptional;
    }

}
