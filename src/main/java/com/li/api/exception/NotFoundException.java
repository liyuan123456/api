package com.li.api.exception;

/**
 * @author 黎源
 * @date 2020/11/13 17:05
 */
public class NotFoundException extends HttpExcpetion {
    public NotFoundException(int code){
        this.code = code;
        this.httpStatusCode = 404;
    }
}
