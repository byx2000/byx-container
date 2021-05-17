package byx.ioc.extension.aop.annotation;

import byx.ioc.annotation.Component;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 指定增强类
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Component
public @interface AdviceBy {
    /**
     * 增强类
     */
    Class<?> value();
}
