package com.li.api.util;

import com.auth0.jwt.JWT;
import com.auth0.jwt.JWTVerifier;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.exceptions.JWTVerificationException;
import com.auth0.jwt.interfaces.Claim;
import com.auth0.jwt.interfaces.DecodedJWT;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.lang.reflect.MalformedParameterizedTypeException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * @author 黎源
 * @date 2020/12/22 20:33
 */
@Component
public class JwtToken {


    private static String secret;
    private static Integer timeout;
    private static final Integer defaultScope = 8;

    @Value("${myjwt.secret}")
    public void setSecret(String secret) {
        JwtToken.secret = secret;
    }
    @Value("${myjwt.timeout}")
    public void setTimeout(Integer timeout) {
        JwtToken.timeout = timeout;
    }

    public static String makeToken(Long uid, Integer scope) {
        return getToken(uid, scope);
    }

    public static String makeToken(Long uid) {
        return getToken(uid, JwtToken.defaultScope);
    }

    /**
     * 制作jwt令牌
     * @param uid
     * @param scope
     * @return
     */
    private static String getToken(Long uid, Integer scope) {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        Map<String, Date> map = JwtToken.calculateExpiredIssues();
        return JWT.create()
                .withClaim("uid", uid)
                .withClaim("scope", scope)
                .withExpiresAt(map.get("expiredTime"))
                .withIssuedAt(map.get("now"))
                .sign(algorithm);
    }

    /**
     * 运算令牌有效期
     * @return
     */
    private static Map<String, Date> calculateExpiredIssues() {
        Map map = new HashMap<String,Date>();
        Calendar calendar = Calendar.getInstance();
        Date now = calendar.getTime();
        calendar.add(Calendar.MILLISECOND,timeout);
        map.put("now", now);
        map.put("expiredTime", calendar.getTime());
        return map;
    }

    /**
     * 令牌校验
     * @param token
     * @return
     * @throws JWTVerificationException
     */
    public static Map<String,Claim> getClaim(String token) throws JWTVerificationException {
        Algorithm algorithm = Algorithm.HMAC256(secret);
        JWTVerifier jwtVerifier = JWT.require(algorithm).build();
        DecodedJWT decodedJWT = jwtVerifier.verify(token);
        return decodedJWT.getClaims();
    }
}
