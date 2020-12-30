package com.li.api.validator;

import org.springframework.util.StringUtils;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 黎源
 * @date 2020/12/16 21:01
 */
public class TokenPasswordConstraint implements ConstraintValidator<TokenPassword, String> {
    private Integer min;
    private Integer max;
    @Override
    public void initialize(TokenPassword constraintAnnotation) {
        this.min = constraintAnnotation.min();
        this.max = constraintAnnotation.max();
    }

    @Override
    public boolean isValid(String value, ConstraintValidatorContext context) {
        if(StringUtils.isEmpty(value) || (value.length() <= this.max && value.length() >= this.min)){
            return true;
        }
        return false;
    }
}
