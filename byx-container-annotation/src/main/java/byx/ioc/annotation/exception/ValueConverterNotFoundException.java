package byx.ioc.annotation.exception;

import byx.ioc.exception.ByxContainerException;

/**
 * 找不到类型转换器
 *
 * @author byx
 */
public class ValueConverterNotFoundException extends ByxContainerException {
    public ValueConverterNotFoundException(Class<?> fromType, Class<?> toType) {
        super("Cannot find ValueConverter from " + fromType + " to " + toType);
    }
}
