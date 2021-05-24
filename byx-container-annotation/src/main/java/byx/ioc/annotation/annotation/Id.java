package byx.ioc.annotation.annotation;

import java.lang.annotation.*;

/**
 * 指定id
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.PARAMETER, ElementType.TYPE, ElementType.METHOD, ElementType.FIELD})
@Documented
public @interface Id {
    String value();
}
