package com.li.api.enums;

/**
 * @author 黎源
 * @date 2020/12/18 15:52
 */
public enum LoginType {
    USER_WX(0,"微信登陆"),
    USER_EMAIL(1,"邮箱登录"),
    USER_PHONE_NUMBER(2,"电话登录");
    private Integer code;
    private String value;

    LoginType(Integer code, String value) {
        this.code = code;
        this.value = value;
    }
}
