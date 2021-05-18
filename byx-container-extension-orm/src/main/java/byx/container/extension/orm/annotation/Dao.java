package byx.container.extension.orm.annotation;

import byx.ioc.annotation.Component;

import java.lang.annotation.*;

/**
 * 用于指定自动生成Dao实现类
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Dao {
}
