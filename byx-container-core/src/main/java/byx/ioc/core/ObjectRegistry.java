package byx.ioc.core;

/**
 * 对象注册器
 * 用于向容器中注册对象
 *
 * @author byx
 */
public interface ObjectRegistry<D> {
    /**
     * 注册对象
     *
     * @param id id
     * @param definition 对象定义
     */
    void registerObject(String id, D definition);
}
