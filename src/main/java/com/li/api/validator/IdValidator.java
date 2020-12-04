package com.li.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 黎源
 * @date 2020/11/29 21:15
 */
@Documented
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.PARAMETER)
@Constraint(validatedBy = IdConstraint.class)
public @interface IdValidator {
        String message() default "Id必须为正整数";

        Class<?>[] groups() default {};

        Class<? extends Payload>[] payload() default {};
}
