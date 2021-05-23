package byx.ioc.core;

import byx.ioc.util.AnnotationScanner;

/**
 * 包扫描和容器上下文信息
 *
 * @author byx
 */
public class PackageContext {
    private final Container container;
    private final AnnotationScanner annotationScanner;
    private final ObjectRegistry registry;

    public PackageContext(Container container, AnnotationScanner annotationScanner, ObjectRegistry registry) {
        this.container = container;
        this.annotationScanner = annotationScanner;
        this.registry = registry;
    }

    public Container getContainer() {
        return container;
    }

    public AnnotationScanner getAnnotationScanner() {
        return annotationScanner;
    }

    public ObjectRegistry getRegistry() {
        return registry;
    }
}
