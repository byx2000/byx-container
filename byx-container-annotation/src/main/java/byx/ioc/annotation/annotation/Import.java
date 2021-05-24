package byx.ioc.annotation.annotation;

import java.lang.annotation.*;

/**
 * 导入组件
 *
 * @author byx
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Component
public @interface Import {
    Class<?>[] value();
}
