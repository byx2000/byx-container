package byx.ioc.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 用于指定组件之间的相对顺序
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
public @interface Order {
    /**
     * 在当前组件之前的组件
     */
    Class<?>[] before() default {};

    /**
     * 在当前组件之后的组件
     */
    Class<?>[] after() default {};
}
