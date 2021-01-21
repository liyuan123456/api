package com.li.api.core.interceptors;

import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.li.api.core.UserLocal;
import com.li.api.exception.ForbiddenException;
import com.li.api.exception.Unauthorized;
import com.li.api.pojo.model.User;
import com.li.api.service.UserServiceImpl;
import com.li.api.util.JwtToken;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.handler.HandlerInterceptorAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Map;
import java.util.Optional;

/**
 * @author 黎源
 * @date 2020/12/25 13:39
 */
public class PermissionInterceptor extends HandlerInterceptorAdapter {

    @Autowired
    private UserServiceImpl service;

    public PermissionInterceptor() {
        super();
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        //访问的接口是否是公开的接口
        Optional<ScopeLevel> optionalScopeLevel = getScopeLevel(handler);
        if (!optionalScopeLevel.isPresent()) {
            return true;
        }
        //请求是否携带了令牌 令牌是否合法
        Optional<Map<String, Claim>> optionalMap = isValidToken(request);
        //限级别是否足够
        Boolean valid = isPermission(optionalScopeLevel.get(), optionalMap.get());
        if (!valid) {
            throw new ForbiddenException(10004);
        }
        setUserLocal(optionalMap.get());
        return true;
    }


    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        UserLocal.clear();
        super.postHandle(request, response, handler, modelAndView);
    }


    public void setUserLocal(Map<String,Claim> claimMap) {
        Long uid = claimMap.get("uid").asLong();
        Integer scope = claimMap.get("scope").asInt();
        Optional<User> userOptional = service.getUserById(uid);
        if(userOptional.isPresent()){
            UserLocal.setUser(userOptional.get(),scope);
        }
    }

    /**
     * 判断要访问的接口是否是公开的
     *
     * @param handler
     * @return
     */
    private Optional getScopeLevel(Object handler) {
        if (handler instanceof HandlerMethod) {
            ScopeLevel scopeLevel = ((HandlerMethod) handler).getMethod().getAnnotation(ScopeLevel.class);
            if (scopeLevel == null) {
                return Optional.empty();
            }
            return Optional.of(scopeLevel);
        }
        return Optional.empty();
    }



    /**
     * 判断请求是否合法
     *
     * @param request
     * @return
     */
    private Optional<Map<String, Claim>> isValidToken(HttpServletRequest request) {
        String bearerToken = request.getHeader("Authorization");
        if (StringUtils.isEmpty(bearerToken)) {
            throw new Unauthorized(10003);
        }
        if (!bearerToken.startsWith("Bearer")) {
            throw new Unauthorized(10003);
        }
        String[] tokens = bearerToken.split(" ");
        if (tokens.length != 2) {
            throw new Unauthorized(10003);
        }

        Map<String, Claim> map;
        try {
            map = JwtToken.getClaim(tokens[1]);
        } catch (JWTVerificationException e) {
            throw new Unauthorized(10003);
        }
        return Optional.of(map);
    }

    /**
     * 判断权限级别
     *
     * @param scopeLevel
     * @param claimMap
     * @return
     */
    private Boolean isPermission(ScopeLevel scopeLevel, Map<String, Claim> claimMap) {
        return claimMap.get("scope").asInt() >= scopeLevel.value();
    }
}
