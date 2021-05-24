package byx.ioc.annotation.converter;

import byx.ioc.core.ValueConverter;

/**
 * String -> int
 *
 * @author byx
 */
public class StringToInt implements ValueConverter {
    @Override
    public Class<?> fromType() {
        return String.class;
    }

    @Override
    public Class<?> toType() {
        return int.class;
    }

    @Override
    public Object convert(Object obj) {
        return Integer.parseInt((String) obj);
    }

    @Override
    public Object convertBack(Object obj) {
        return obj.toString();
    }
}
