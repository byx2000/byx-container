package byx.ioc.annotation.core;

/**
 * AnnotationConfigContainer回调
 *
 * @author byx
 */
public interface AnnotationConfigContainerCallback {
    /**
     * 注解扫描容器初始化完成后回调
     * @param ctx 容器上下文
     */
    void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx);
}
