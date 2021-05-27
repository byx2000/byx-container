package byx.ioc.core;

import java.util.List;

/**
 * 容器上下文信息
 * AbstractContainer的子类需要定义自己的ContainerContext
 *
 * @author byx
 */
public interface ContainerContext {
    /**
     * 获取容器
     */
    Container getContainer();

    /**
     * 获取对象注册器
     */
    ObjectRegistry getRegistry();

    /**
     * 获取对象回调器
     */
    List<ObjectCallback> getObjectCallbacks();

    /**
     * 获取容器回调器
     */
    List<ContainerCallback> getContainerCallbacks();

    /**
     * 获取值转换器
     */
    List<ValueConverter> getValueConverters();

    /**
     * 获取额外导入的组件
     */
    List<Class<?>> getImportComponents();
}
