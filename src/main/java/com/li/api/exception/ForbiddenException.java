package com.li.api.exception;

/**
 * @author 黎源
 * @date 2020/11/13 17:07
 */
public class ForbiddenException extends HttpExcpetion {
    public ForbiddenException(int code){
        this.code = code;
        this.httpStatusCode = 403;
    }
}
