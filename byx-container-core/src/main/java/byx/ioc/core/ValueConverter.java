package byx.ioc.core;

/**
 * 类型转换器：把String转换为指定类型
 * 与Value注解配合使用
 *
 * @author byx
 */
public interface ValueConverter {
    /**
     * 源类型
     *
     * @return 原类型
     */
    Class<?> fromType();

    /**
     * 目标类型
     *
     * @return 目标类型
     */
    Class<?> toType();

    /**
     * 正向转换
     *
     * @param obj 源对象
     * @return 转换后的对象
     */
    Object convert(Object obj);

    /**
     * 反向转换
     *
     * @param obj 转换后的对象
     * @return 源对象
     */
    Object convertBack(Object obj);
}
