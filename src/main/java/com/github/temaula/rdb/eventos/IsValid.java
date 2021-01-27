package com.github.temaula.rdb.eventos;

import javax.validation.Constraint;
import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import javax.validation.Payload;
import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;
import java.lang.reflect.Constructor;
import java.lang.reflect.InvocationTargetException;
import java.util.Arrays;
import java.util.Optional;
import java.util.stream.Collectors;

@Constraint(validatedBy = IsValid.IsValidConstraintValidator.class)
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.TYPE})
public @interface IsValid {

    String message() default "não está valido";

    Class<? extends Payload>[] payload() default {};

    Class<?>[] groups() default {};

    Class<? extends CustomValidator> by();

    Class<? extends Attribute>[] attributes() default {};

    public static interface CustomValidator {
        boolean isValid(Object value, Attribute[] attributes);
    }

    public static interface Attribute {
        Optional<?> getValue(Object source);
    }

    public static class IsValidConstraintValidator implements ConstraintValidator<IsValid, Object> {

        private IsValid constraintAnnotation;

        @Override
        public void initialize(IsValid constraintAnnotation) {

            this.constraintAnnotation = constraintAnnotation;
        }

        @Override
        public boolean isValid(Object value, ConstraintValidatorContext context) {

            final Class<? extends CustomValidator> aClass = constraintAnnotation.by();
            final CustomValidator customValidator = newInstance(aClass);
            return customValidator.isValid(value,
                    Arrays.stream(constraintAnnotation.attributes())
                            .map(this::newInstance)
                            .collect(Collectors.toList()).toArray(new Attribute[0]));
        }

        private <T> T newInstance(
                Class<T> aClass) {
            try {
                final Constructor<? extends T> constructor = aClass
                        .getConstructor(new Class<?>[0]);
                constructor.setAccessible(true);
                final T instance = constructor.newInstance(new Object[0]);
                return instance;
            } catch (InstantiationException | IllegalAccessException | InvocationTargetException | NoSuchMethodException e) {
                throw new RuntimeException("Can not instantiate the " + aClass.toString() + " : " + e.getMessage(), e);
            }
        }
    }
}
