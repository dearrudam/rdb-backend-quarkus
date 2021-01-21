package com.temaula.rdb.validation;

import com.temaula.rdb.validation.constraints.Unique;
import io.quarkus.hibernate.orm.panache.runtime.JpaOperations;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Map;

public class UniqueConstraintValidator implements ConstraintValidator<Unique, Object> {

    private Unique annotation;

    @Override
    public void initialize(Unique annotation) {
        this.annotation = annotation;
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {

        String query = this.annotation.fieldName() + " = :value";

        if (value instanceof String) {
            if (this.annotation.ignoreCase())
                query = "lower(" + this.annotation.fieldName() + ") = lower(:value)";
        }

        var exists = JpaOperations
                .exists(this.annotation.entityType(),
                        query,
                        Map.of("value", value));

        return !exists;
    }
}
