package com.li.api.core;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.stereotype.Component;

import javax.servlet.http.HttpServletRequest;

/**
 * @author 黎源
 * @date 2020/11/13 17:49
 */

@Builder
@NoArgsConstructor
@AllArgsConstructor
@Data
public class UnifyResponse {
    private Integer code;
    private String message;
    private String request;

    public static UnifyResponse OK(HttpServletRequest httpRequest) {
        return UnifyResponse.builder()
                .code(200)
                .message("成功")
                .request(httpRequest.getRequestURI())
                .build();
    }

    public static UnifyResponse OK(Integer code,HttpServletRequest httpRequest) {
        return UnifyResponse.builder()
                .code(code)
                .message("成功")
                .request(httpRequest.getRequestURI())
                .build();
    }

    public static UnifyResponse OK(Integer code,String message,HttpServletRequest httpRequest) {
        return UnifyResponse.builder()
                .code(code)
                .message(message)
                .request(httpRequest.getRequestURI())
                .build();
    }
}
