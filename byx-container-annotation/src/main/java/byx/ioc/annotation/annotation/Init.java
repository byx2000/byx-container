package byx.ioc.annotation.annotation;

import java.lang.annotation.*;

/**
 * 指定初始化方法
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
@Documented
public @interface Init {
}
