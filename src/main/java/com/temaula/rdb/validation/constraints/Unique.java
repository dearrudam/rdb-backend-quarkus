package com.temaula.rdb.validation.constraints;

import com.temaula.rdb.validation.UniqueConstraintValidator;

import javax.validation.Constraint;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Constraint(validatedBy = UniqueConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.PARAMETER})
public @interface Unique {

    public String message() default "campo deve ser único";

    public Class<?> entityType();

    public String fieldName();

    public boolean ignoreCase() default true;

    public Class<? extends Payload>[] payload() default {};

    public Class<?>[] groups() default {};

}
