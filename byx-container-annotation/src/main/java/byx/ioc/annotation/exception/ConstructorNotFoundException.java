package byx.ioc.annotation.exception;

import byx.ioc.exception.ByxContainerException;

/**
 * 找不到构造函数
 *
 * @author byx
 */
public class ConstructorNotFoundException extends ByxContainerException {
    public ConstructorNotFoundException(Class<?> type) {
        super("Cannot find properly constructor in class " + type.getCanonicalName());
    }
}
