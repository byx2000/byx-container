package byx.ioc.core;

/**
 * 注解扫描容器回调
 *
 * @author byx
 */
public interface AnnotationConfigContainerCallback extends ContainerCallback {
    /**
     * 注解扫描容器初始化完成后回调
     * @param ctx 上下文
     */
    void afterAnnotationConfigContainerInit(PackageContext ctx);
}
