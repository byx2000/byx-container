package byx.ioc.annotation.core;

import byx.ioc.annotation.util.AnnotationScanner;
import byx.ioc.core.AbstractContainer;

/**
 * 通过注解来配置的容器
 *
 * @author byx
 */
public class AnnotationConfigContainer extends AbstractContainer implements AnnotationConfigContainerContext {
    private final AnnotationScanner scanner;

    /**
     * 创建一个AnnotationConfigContainer
     *
     * @param basePackage 基准包名
     */
    public AnnotationConfigContainer(String basePackage) {
        // 初始化注解扫描器
        scanner = new AnnotationScanner(basePackage);

        // 容器初始化完毕
        afterAnnotationConfigContainerInit();
    }

    /**
     * 创建一个AnnotationConfigContainer
     *
     * @param baseClass 基准类
     */
    public AnnotationConfigContainer(Class<?> baseClass) {
        this(baseClass.getPackageName());
    }

    /**
     * 注解扫描容器初始化后回调AnnotationConfigContainerCallback
     */
    private void afterAnnotationConfigContainerInit() {
        // 回调所有AnnotationConfigContainerCallback
        getContainerCallbacks().stream()
                .filter(c -> c instanceof AnnotationConfigContainerCallback)
                .forEach(c -> ((AnnotationConfigContainerCallback) c).afterAnnotationConfigContainerInit(this));
    }

    @Override
    public AnnotationScanner getAnnotationScanner() {
        return scanner;
    }
}
