package byx.ioc.annotation.core;

import byx.ioc.annotation.util.AnnotationScanner;
import byx.ioc.core.ContainerContext;

/**
 * AnnotationConfigContainer上下文信息
 *
 * @author byx
 */
public interface AnnotationConfigContainerContext extends ContainerContext {
    /**
     * 获取AnnotationScanner
     */
    AnnotationScanner getAnnotationScanner();
}
