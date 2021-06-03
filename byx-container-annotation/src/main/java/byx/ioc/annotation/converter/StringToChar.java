package byx.ioc.annotation.converter;

import byx.ioc.annotation.core.ValueConverter;

/**
 * String -> char
 *
 * @author byx
 */
public class StringToChar implements ValueConverter {
    @Override
    public Class<?> fromType() {
        return String.class;
    }

    @Override
    public Class<?> toType() {
        return char.class;
    }

    @Override
    public Object convert(Object obj) {
        return ((String) obj).charAt(0);
    }

    @Override
    public Object convertBack(Object obj) {
        return obj.toString();
    }
}
