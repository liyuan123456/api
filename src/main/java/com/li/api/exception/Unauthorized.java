package com.li.api.exception;

/**
 * @author 黎源
 * @date 2020/12/25 14:42
 */
public class Unauthorized extends HttpExcpetion {
    public Unauthorized(Integer code) {
        this.code = code;
        this.httpStatusCode = 401;
    }
}
