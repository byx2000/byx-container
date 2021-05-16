package byx.ioc.core;

/**
 * 对象注册器，用于动态地向容器中注册对象
 * 与ContainerCallback配合使用
 *
 * @author byx
 */
public interface ObjectRegistry {
    /**
     * 注册对象
     *
     * @param id id
     * @param definition 对象定义
     */
    void registerObject(String id, ObjectDefinition definition);
}
