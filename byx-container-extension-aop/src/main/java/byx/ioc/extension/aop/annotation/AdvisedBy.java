package byx.ioc.extension.aop.annotation;

import byx.ioc.annotation.Component;

import java.lang.annotation.*;

/**
 * 指定增强类
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface AdvisedBy {
    Class<?> value();
}
