package byx.ioc.annotation.core;

import byx.ioc.annotation.util.AnnotationScanner;
import byx.ioc.core.CachedContainer;
import byx.ioc.core.Container;
import byx.ioc.core.Dependency;
import byx.ioc.core.ObjectRegistry;
import byx.ioc.util.ExtensionLoader;

import java.util.List;

/**
 * 通过注解来配置的容器
 *
 * @author byx
 */
public class AnnotationConfigContainer extends CachedContainer<ObjectDefinition> implements AnnotationConfigContainerContext {
    /**
     * 注解扫描器
     */
    private final AnnotationScanner scanner;

    private static final List<ObjectCallback> OBJECT_CALLBACKS;
    private static final List<AnnotationConfigContainerCallback> CONTAINER_CALLBACKS;

    static {
        // 加载扩展
        OBJECT_CALLBACKS = ExtensionLoader.getExtensionInstancesOfType(ObjectCallback.class);
        CONTAINER_CALLBACKS = ExtensionLoader.getExtensionInstancesOfType(AnnotationConfigContainerCallback.class);
    }

    /**
     * 使用指定包创建AnnotationConfigContainer
     *
     * @param baseClass 基准类
     */
    public AnnotationConfigContainer(Class<?> baseClass) {
        this(baseClass.getPackageName());
    }

    /**
     * 使用指定包创建AnnotationConfigContainer
     *
     * @param basePackage 基准包名
     */
    public AnnotationConfigContainer(String basePackage) {
        scanner = new AnnotationScanner(basePackage);
        afterAnnotationConfigContainerInit();
    }

    @Override
    protected Class<?> getType(ObjectDefinition definition) {
        return definition.getType();
    }

    @Override
    protected Dependency[] getDependencies(ObjectDefinition definition) {
        return definition.getDependencies();
    }

    @Override
    protected Object doInstantiate(ObjectDefinition definition, String id, Object[] dependencies) {
        return definition.getInstance(dependencies);
    }

    @Override
    protected void doInit(ObjectDefinition definition, String id, Object obj) {
        definition.doInit(obj);

        // 执行扩展回调
        OBJECT_CALLBACKS
                .forEach(c -> c.afterObjectInit(
                        new ObjectContext(obj, definition.getType(), this, definition, id)));
    }

    @Override
    protected Object doWrap(ObjectDefinition definition, String id, Object obj) {
        final Object[] o = {definition.doWrap(obj)};

        // 执行扩展回调
        OBJECT_CALLBACKS
                .forEach(c -> o[0] = c.afterObjectWrap(
                        new ObjectContext(o[0], definition.getType(), this, definition, id)));
        return o[0];
    }

    @Override
    public Container getContainer() {
        return this;
    }

    @Override
    public ObjectRegistry<ObjectDefinition> getObjectRegistry() {
        return this;
    }

    @Override
    public AnnotationScanner getAnnotationScanner() {
        return scanner;
    }

    @Override
    public void registerObject(String id, ObjectDefinition definition) {
        super.registerObject(id, definition);
    }

    private void afterAnnotationConfigContainerInit() {
        // 回调所有AnnotationConfigContainerCallback
        CONTAINER_CALLBACKS.forEach(c -> c.afterAnnotationConfigContainerInit(this));
    }
}
