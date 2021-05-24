package byx.ioc.extension.aop.annotation;

import java.lang.annotation.*;

/**
 * 指定接口的实现类
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
public @interface ImplementedBy {
    Class<?> value();
}
