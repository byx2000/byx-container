package byx.ioc.annotation.annotation;

import java.lang.annotation.*;

/**
 * 自动装配
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.FIELD, ElementType.CONSTRUCTOR, ElementType.METHOD})
@Documented
public @interface Autowired {
}
