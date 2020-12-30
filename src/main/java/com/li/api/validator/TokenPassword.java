package com.li.api.validator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.*;

/**
 * @author 黎源
 * @date 2020/12/16 20:58
 */
@Documented
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = TokenPasswordConstraint.class)
public @interface TokenPassword {
    String message() default "不符合规范";

    int min() default 6;

    int max() default 12;

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
