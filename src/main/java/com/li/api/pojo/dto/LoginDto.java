package com.li.api.pojo.dto;

import com.li.api.enums.LoginType;
import com.li.api.validator.TokenPassword;
import lombok.Getter;
import lombok.Setter;

import javax.validation.constraints.NotBlank;

/**
 * @author 黎源
 * @date 2020/12/18 15:46
 */
@Getter
@Setter
public class LoginDto {
    @NotBlank(message = "account不能为空")
    private String account;
    @TokenPassword(message = "密码格式错误!")
    private String password;
    private LoginType type;
}
