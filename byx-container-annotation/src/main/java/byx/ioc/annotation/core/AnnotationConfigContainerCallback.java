package byx.ioc.annotation.core;

import byx.ioc.core.ContainerCallback;

/**
 * AnnotationConfigContainer回调
 *
 * @author byx
 */
public interface AnnotationConfigContainerCallback extends ContainerCallback {
    /**
     * 注解扫描容器初始化完成后回调
     * @param ctx 容器上下文
     */
    void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx);
}
