package byx.ioc.core;

/**
 * 类型转换器：把String转换为指定类型
 * 与Value注解配合使用
 *
 * @author byx
 */
public interface ValueConverter {
    Class<?> fromType();
    Class<?> toType();
    Object convert(Object obj);
    Object convertBack(Object obj);
}
