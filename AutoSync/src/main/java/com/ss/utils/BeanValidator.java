package com.ss.utils;

import com.google.common.base.Preconditions;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.ss.code.ErrorCode;
import com.ss.exception.CommonResultException;
import org.apache.commons.collections.MapUtils;

import javax.validation.ConstraintViolation;
import javax.validation.Validation;
import javax.validation.Validator;
import javax.validation.ValidatorFactory;
import java.util.*;

public class BeanValidator {

    private static final ValidatorFactory validatorFactory = Validation.buildDefaultValidatorFactory();

    public static <T> Map<String, String> validate(T t, Class... groups) {
        Validator validator = validatorFactory.getValidator();
        Set validatorResult = validator.validate(t, groups);
        if (validatorResult.isEmpty()) {
            return Collections.emptyMap();
        } else {
            LinkedHashMap errors = Maps.newLinkedHashMap();
            Iterator iterator = validatorResult.iterator();
            while (iterator.hasNext()) {
                ConstraintViolation constraintViolation = (ConstraintViolation) iterator.next();
                errors.put(constraintViolation.getPropertyPath().toString(), constraintViolation.getMessage());
            }
            return errors;
        }
    }

    public static Map<String, String> validateList(Collection<?> collection){
        Preconditions.checkNotNull(collection);
        Iterator iterator = collection.iterator();
        Map errors;
        do {
            if (!iterator.hasNext()){
                return Collections.emptyMap();
            }
            Object object = iterator.next();
            errors = validate(object, new Class[0]);
        }while (errors.isEmpty());
        return errors;
    }

    public static Map<String, String> validateObject(Object first, Object... objects){
        if(objects != null && objects.length>0){
            return validateList(Lists.asList(first,objects));
        }else {
            return validate(first,new Class[0]);
        }
    }

    public static void check(Object param) throws CommonResultException{
        Map<String, String> map = validateObject(param);
        if (MapUtils.isNotEmpty(map)){
            throw new CommonResultException(ErrorCode.ERROR_PARAM);
        }
    }
}
