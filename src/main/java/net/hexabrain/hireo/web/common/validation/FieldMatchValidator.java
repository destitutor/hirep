package net.hexabrain.hireo.web.common.validation;

import net.hexabrain.hireo.web.common.validation.constraints.FieldMatch;
import org.springframework.beans.BeanWrapper;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyAccessorFactory;

import javax.validation.ConstraintValidator;
import javax.validation.ConstraintValidatorContext;
import java.util.Objects;

public class FieldMatchValidator implements ConstraintValidator<FieldMatch, Object> {
    private String firstFieldName;
    private String secondFieldName;

    @Override
    public void initialize(FieldMatch constraintAnnotation) {
        this.firstFieldName = constraintAnnotation.first();
        this.secondFieldName = constraintAnnotation.second();
    }

    @Override
    public boolean isValid(Object value, ConstraintValidatorContext context) {
        final BeanWrapper wrapper = PropertyAccessorFactory.forBeanPropertyAccess(value);

        try {
            final Object firstValue = wrapper.getPropertyValue(firstFieldName);
            final Object secondValue = wrapper.getPropertyValue(secondFieldName);

            return Objects.equals(firstValue, secondValue);
        } catch (BeansException e) {
            return false;
        }
    }
}
