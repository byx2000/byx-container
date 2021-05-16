package byx.ioc.core;

/**
 * 容器回调器，用于扩展容器的功能
 *
 * @author byx
 */
public interface ContainerCallback {
    /**
     * 容器初始化完后回调
     * @param container 容器
     * @param registry 对象注册器
     */
    default void afterContainerInit(Container container, ObjectRegistry registry) {

    }

    /**
     * 指定回调器执行的顺序，数字小的先执行
     * @return 顺序值
     */
    default int getOrder() {
        return 1;
    }
}
