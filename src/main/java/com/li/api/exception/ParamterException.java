package com.li.api.exception;

/**
 * @author 黎源
 * @date 2020/12/22 14:59
 */
public class ParamterException extends HttpExcpetion {
    public ParamterException(int code){
        this.code = code;
        this.httpStatusCode = 400;
    }

}
