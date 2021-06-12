package byx.ioc.annotation.util;

import java.lang.annotation.Annotation;
import java.util.function.Predicate;

/**
 * 预定义的Predicate&lt;Class&lt;?&gt;&gt;
 *
 * @author byx
 */
public class ClassPredicates {
    /**
     * 匹配所有类
     */
    public static Predicate<Class<?>> all() {
        return c -> true;
    }

    /**
     * 匹配标注了指定注解的类
     */
    public static Predicate<Class<?>> hasAnnotation(Class<? extends Annotation> annotationType) {
        return c -> AnnotationUtils.hasAnnotation(c, annotationType);
    }

    /**
     * 匹配属于指定子类型的类
     */
    public static Predicate<Class<?>> isAssignableTo(Class<?> type) {
        return type::isAssignableFrom;
    }

    /**
     * 匹配特定类型的类
     */
    public static Predicate<Class<?>> sameAs(Class<?> type) {
        return c -> c == type;
    }
}
