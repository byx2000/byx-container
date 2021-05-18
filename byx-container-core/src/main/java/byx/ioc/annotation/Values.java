package byx.ioc.annotation;

import java.lang.annotation.*;

/**
 * 多个Value的容器
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Values {
    Value[] value();
}
