package byx.ioc.annotation.core;

import byx.ioc.annotation.util.AnnotationScanner;
import byx.ioc.core.*;

import java.util.List;

/**
 * 通过注解来配置的容器
 *
 * @author byx
 */
public class AnnotationConfigContainer extends AbstractContainer implements ObjectRegistry, AnnotationConfigContainerContext {
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
    public void registerObject(String id, ObjectDefinition definition) {
        super.registerObject(id, definition);
    }

    // 从AnnotationConfigContainerContext继承 开始

    @Override
    public Container getContainer() {
        return this;
    }

    @Override
    public ObjectRegistry getObjectRegistry() {
        return this;
    }

    @Override
    public AnnotationScanner getAnnotationScanner() {
        return scanner;
    }

    @Override
    public List<ObjectCallback> getObjectCallbacks() {
        return super.getObjectCallbacks();
    }

    @Override
    public List<ContainerCallback> getContainerCallbacks() {
        return super.getContainerCallbacks();
    }

    @Override
    public List<ValueConverter> getValueConverters() {
        return super.getValueConverters();
    }

    @Override
    public List<Class<?>> getImportComponents() {
        return super.getImportComponents();
    }

    // 从AnnotationConfigContainerContext继承 结束
}
