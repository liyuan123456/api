package com.li.api.validator;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;

/**
 * @author 黎源
 * @date 2020/11/29 21:17
 */
public class IdConstraint implements ConstraintValidator<IdValidator,Long>{
    @Override
    public boolean isValid(Long value, ConstraintValidatorContext context) {
        return value >= 0;
    }
}
