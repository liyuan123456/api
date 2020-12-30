package com.li.api.service;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.li.api.exception.ForbiddenException;
import com.li.api.exception.ParamterException;
import com.li.api.pojo.dto.LoginDto;
import com.li.api.pojo.model.User;
import com.li.api.repository.UserRepository;
import com.li.api.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.text.MessageFormat;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/18 22:54
 */
@Service
public class WxAuthentication implements Authentication {
    @Value("${wx.url}")
    private String url;
    @Value("${wx.appid}")
    private String appid;
    @Value("${wx.secret}")
    private String secret;
    @Autowired
    private ObjectMapper mapper;
    @Autowired
    private UserRepository repository;

    /**
     * 请求微信服务器获取openid
     * @param loginDto
     * @return
     */
    @Override
    public String getToken(LoginDto loginDto) {
        String code2session = MessageFormat.format(url, appid, secret, loginDto.getAccount());
        RestTemplate restTemplate = new RestTemplate();
        String resposne = restTemplate.getForObject(code2session, String.class);
        Map session = new HashMap();
        try {
            session = mapper.readValue(resposne, Map.class);
            return registered(session);
        } catch (JsonProcessingException e) {
            e.printStackTrace();
        }
        return registered(session);
    }

    /**
     * 注册并签发jwt令牌
     * @param session
     * @return
     */
    private String registered(Map<String, Object> session) {
        String openid = (String) session.get("openid");
        if(openid == null){
            throw new ParamterException(20001);
        }
        Optional<User> userByOpenid = repository.findUserByOpenid(openid);
        if(userByOpenid.isPresent()){
            return JwtToken.makeToken(userByOpenid.get().getId());
        }else{
            User user = User.builder().openid(openid).build();
            repository.save(user);
            return JwtToken.makeToken(user.getId());
        }
    }
}
