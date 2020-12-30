package com.li.api.exception;

/**
 * @author 黎源
 * @date 2020/12/6 14:42
 */
public class ServerException extends HttpExcpetion {
    public ServerException(int code){
        this.code = code;
        this.httpStatusCode = 500;
    }
}
