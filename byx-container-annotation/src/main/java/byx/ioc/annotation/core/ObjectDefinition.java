package byx.ioc.annotation.core;

import byx.ioc.core.Dependency;

/**
 * 封装对象实例化、初始化和包装逻辑
 *
 * @author byx
 */
public interface ObjectDefinition {
    /**
     * 对象类型
     *
     * @return 类型
     */
    Class<?> getType();

    /**
     * 获取依赖项
     *
     * @return 依赖项数组
     * @see Dependency
     */
    default Dependency[] getDependencies() {
        return new Dependency[0];
    }

    /**
     * 实例化
     *
     * @param dependencies 依赖项
     * @return 实例化的对象
     */
    Object getInstance(Object[] dependencies);

    /**
     * 初始化
     *
     * @param obj 实例化后的对象
     */
    default void doInit(Object obj) {

    }

    /**
     * 替换对象
     *
     * @param obj 初始化后的对象
     * @return 包装后的对象
     */
    default Object doReplace(Object obj) {
        return obj;
    }
}
