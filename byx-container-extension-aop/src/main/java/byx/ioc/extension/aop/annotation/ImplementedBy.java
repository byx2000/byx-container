package byx.ioc.extension.aop.annotation;

import byx.ioc.annotation.Component;

import java.lang.annotation.*;

/**
 * 指定接口的实现类
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface ImplementedBy {
    Class<?> value();
}
