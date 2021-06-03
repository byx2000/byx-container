package byx.ioc.annotation.core;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.util.AnnotationScanner;
import byx.ioc.core.*;
import byx.ioc.util.ExtensionLoader;

import java.util.List;

/**
 * 通过注解来配置的容器
 *
 * @author byx
 */
public class AnnotationConfigContainer
        extends CircularDependencyResolvableContainer<ObjectDefinition>
        implements ObjectRegistry, AnnotationConfigContainerContext {
    /**
     * 注解扫描器
     */
    private final AnnotationScanner scanner;

    private static final List<ObjectCallback> OBJECT_CALLBACKS;
    private static final List<AnnotationConfigContainerCallback> CONTAINER_CALLBACKS;
    private static final List<Class<?>> IMPORT_COMPONENTS;
    private static final List<ValueConverter> VALUE_CONVERTERS;

    static {
        // 加载扩展
        OBJECT_CALLBACKS = ExtensionLoader.getExtensionObjectsOfType(ObjectCallback.class);
        CONTAINER_CALLBACKS = ExtensionLoader.getExtensionObjectsOfType(AnnotationConfigContainerCallback.class);
        IMPORT_COMPONENTS = ExtensionLoader.getExtensionClassesWithAnnotation(Component.class);
        VALUE_CONVERTERS = ExtensionLoader.getExtensionObjectsOfType(ValueConverter.class);
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
        // 初始化注解扫描器
        scanner = new AnnotationScanner(basePackage);

        // 容器初始化完毕
        afterAnnotationConfigContainerInit();
    }

    @Override
    protected Class<?> getType(ObjectDefinition definition) {
        return definition.getType();
    }

    @Override
    protected Dependency[] getDependencies(ObjectDefinition definition) {
        return definition.getInstanceDependencies();
    }

    @Override
    protected Object doInstantiate(ObjectDefinition definition, String id, Object[] params) {
        return definition.getInstance(params);
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
    public ObjectRegistry getObjectRegistry() {
        return this;
    }

    @Override
    public AnnotationScanner getAnnotationScanner() {
        return scanner;
    }

    @Override
    public List<ValueConverter> getValueConverters() {
        return VALUE_CONVERTERS;
    }

    @Override
    public List<Class<?>> getImportComponents() {
        return IMPORT_COMPONENTS;
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
