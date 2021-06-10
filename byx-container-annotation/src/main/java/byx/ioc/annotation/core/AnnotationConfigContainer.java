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
    protected Object getInstance(ObjectDefinition definition, Object[] dependencies, String id) {
        Object obj = definition.getInstance(dependencies);
        OBJECT_CALLBACKS.forEach(c -> c.afterObjectInstantiate(new ObjectContext(obj, this, definition, id)));
        return obj;
    }

    @Override
    protected void doInit(ObjectDefinition definition, Object obj, String id) {
        definition.doInit(obj);
        OBJECT_CALLBACKS.forEach(c -> c.afterObjectInit(new ObjectContext(obj, this, definition, id)));
    }

    @Override
    protected Object doReplace(ObjectDefinition definition, Object obj, String id) {
        final Object[] o = {definition.doReplace(obj)};
        OBJECT_CALLBACKS.forEach(c -> o[0] = c.replaceObject(new ObjectContext(o[0], this, definition, id)));
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
        CONTAINER_CALLBACKS.forEach(c -> c.afterAnnotationConfigContainerInit(this));
    }
}
