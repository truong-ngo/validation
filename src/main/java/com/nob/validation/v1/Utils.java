package com.nob.validation.v1;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Field;

public class Utils {

    public static Boolean resolveExpression(String expression, Object context) {
        try {
            ExpressionParser parser = new SpelExpressionParser();
            Expression exp = parser.parseExpression(expression);
            Boolean result = (Boolean) exp.getValue(context);
            return Boolean.TRUE.equals(result);
        } catch (ParseException | EvaluationException | IllegalAccessError e) {
            return null;
        }
    }

    public static Field getField(String fieldName, Object context) {
        Class<?> clazz = context.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            return getField(fieldName, superClass);
        }
        return null;
    }
}
