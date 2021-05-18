package byx.ioc.annotation;

import java.lang.annotation.*;

/**
 * 组件扫描
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target({ElementType.TYPE, ElementType.METHOD, ElementType.ANNOTATION_TYPE})
@Documented
public @interface Component {
}
