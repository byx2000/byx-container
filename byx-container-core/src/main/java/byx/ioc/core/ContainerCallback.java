package byx.ioc.core;

/**
 * 容器回调器，用于扩展容器的功能
 * AbstractContainer的子类需要定义自己的ContainerCallback
 * 并添加相应的回调方法
 *
 * @author byx
 */
public interface ContainerCallback {
    /**
     * 指定回调器执行的顺序，数字小的先执行
     *
     * @return 顺序值
     */
    default int getOrder() {
        return 1;
    }
}
