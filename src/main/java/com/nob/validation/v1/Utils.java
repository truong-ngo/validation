package com.nob.validation.v1;

import org.springframework.expression.EvaluationException;
import org.springframework.expression.Expression;
import org.springframework.expression.ExpressionParser;
import org.springframework.expression.ParseException;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import java.lang.reflect.Field;

public class Utils {

    /**
     * Resolve boolean SpEl expression
     * @param expression the SpEl expression
     * @param context the expression's context
     * @return {@code Boolean} the expression resolve result, null if expression is invalid
     * */
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

    /**
     * Get property / field of object base on field name
     * @param fieldName property / field name
     * @param o given object
     * @return {@code Field} represent property, null if property is not found
     * */
    public static Field propertyOf(String fieldName, Object o) {
        Class<?> clazz = o.getClass();
        Field[] fields = clazz.getDeclaredFields();
        for (Field field : fields) {
            if (field.getName().equals(fieldName)) {
                return field;
            }
        }
        Class<?> superClass = clazz.getSuperclass();
        if (superClass != null) {
            return propertyOf(fieldName, superClass);
        }
        return null;
    }

    /**
     * Comparison of two represent-typed date object.
     * <p>
     * The represent type can be:
     * <ul>
     *     <li>{@link java.lang.String}</li>
     *     <li>{@link java.lang.Long}</li>
     *     <li>{@link java.util.Date}</li>
     *     <li>{@link java.time.LocalDate}</li>
     *     <li>{@link java.time.LocalTime}</li>
     *     <li>{@link java.time.LocalDateTime}</li>
     *     <li>{@link java.time.ZonedDateTime}</li>
     *     <li>{@link java.time.Instant}</li>
     * </ul>
     * */
    public static boolean dateCompare(Object o1, Object o2) {
        return true;
    }
}
