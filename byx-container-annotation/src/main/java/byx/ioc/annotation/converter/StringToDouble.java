package byx.ioc.annotation.converter;

import byx.ioc.core.ValueConverter;

/**
 * String -> double
 *
 * @author byx
 */
public class StringToDouble implements ValueConverter {
    @Override
    public Class<?> fromType() {
        return String.class;
    }

    @Override
    public Class<?> toType() {
        return double.class;
    }

    @Override
    public Object convert(Object obj) {
        return Double.parseDouble((String) obj);
    }

    @Override
    public Object convertBack(Object obj) {
        return obj.toString();
    }
}
