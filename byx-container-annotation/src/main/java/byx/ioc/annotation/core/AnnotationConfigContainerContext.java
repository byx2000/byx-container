package byx.ioc.annotation.core;

import byx.ioc.annotation.util.PackageScanner;
import byx.ioc.core.Container;
import byx.ioc.core.ObjectRegistry;

/**
 * AnnotationConfigContainer上下文信息
 *
 * @author byx
 */
public interface AnnotationConfigContainerContext {
    /**
     * 获取容器
     *
     * @return 容器
     */
    Container getContainer();

    /**
     * 获取对象注册器
     *
     * @return 对象注册器
     */
    ObjectRegistry<ObjectDefinition> getObjectRegistry();

    /**
     * 获取AnnotationScanner
     *
     * @return AnnotationScanner
     */
    //AnnotationScanner getAnnotationScanner();

    PackageScanner getPackageScanner();
}
