package com.li.api.service;

import com.li.api.pojo.dto.LoginDto;

/**
 * @author 黎源
 * @date 2020/12/18 22:53
 */
public interface Authentication {
    String getToken(LoginDto loginDto);
}
