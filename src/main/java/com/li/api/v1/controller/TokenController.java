package com.li.api.v1.controller;

import com.li.api.exception.NotFoundException;
import com.li.api.pojo.dto.LoginDto;
import com.li.api.service.Authentication;
import com.li.api.service.WxAuthentication;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

/**
 * @author 黎源
 * @date 2020/12/18 15:42
 */
@RestController
@RequestMapping("/v1")
public class TokenController {

    @Autowired
    @Qualifier(value = "wxAuthentication")
    private Authentication authentication;

    @PostMapping("/token")
    public Map<String,String> getToken(@Validated @RequestBody LoginDto loginDto) {
        Map tokenMap = new HashMap<String, String>();
        String token;
        switch (loginDto.getType()) {
            case USER_WX:
                token = authentication.getToken(loginDto);
                tokenMap.put("token", token);
                break;
            // TODO: 2020/12/18  邮箱登录
            // TODO: 2020/12/18  电话号码登录
            default:
                throw new NotFoundException(10002);
        }
        return tokenMap;
    }
}
