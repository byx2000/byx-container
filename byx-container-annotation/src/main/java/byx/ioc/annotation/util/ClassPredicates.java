package byx.ioc.annotation.util;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * @author byx
 */
public class ClassPredicates {
    public static Predicate<Class<?>> hasAnnotation(Class<? extends Annotation> annotationType) {
        return c -> AnnotationUtils.hasAnnotation(c, annotationType);
    }

    public static Predicate<Class<?>> isAssignableTo(Class<?> type) {
        return type::isAssignableFrom;
    }

    public static Predicate<Class<?>> equal(Class<?> type) {
        return c -> c == type;
    }
}
