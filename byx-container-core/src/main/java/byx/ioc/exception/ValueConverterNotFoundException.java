package byx.ioc.exception;

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
