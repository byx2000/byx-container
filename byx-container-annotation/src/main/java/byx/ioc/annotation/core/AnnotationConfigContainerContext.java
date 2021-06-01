package byx.ioc.annotation.core;

import byx.ioc.annotation.util.AnnotationScanner;
import byx.ioc.core.*;

import java.util.List;

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
    ObjectRegistry getObjectRegistry();

    /**
     * 获取AnnotationScanner
     *
     * @return AnnotationScanner
     */
    AnnotationScanner getAnnotationScanner();

    /**
     * 获取对象回调器
     *
     * @return ObjectCallback列表
     */
    List<ObjectCallback> getObjectCallbacks();

    /**
     * 获取容器回调器
     *
     * @return ContainerCallback列表
     */
    List<ContainerCallback> getContainerCallbacks();

    /**
     * 获取值转换器
     *
     * @return ValueConverter列表
     */
    List<ValueConverter> getValueConverters();

    /**
     * 获取额外导入的组件
     *
     * @return 组件列表
     */
    List<Class<?>> getImportComponents();
}
