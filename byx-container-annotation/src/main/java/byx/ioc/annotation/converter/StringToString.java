package byx.ioc.annotation.converter;

import byx.ioc.core.ValueConverter;

/**
 * String -> String
 *
 * @author byx
 */
public class StringToString implements ValueConverter {
    @Override
    public Class<?> fromType() {
        return String.class;
    }

    @Override
    public Class<?> toType() {
        return String.class;
    }

    @Override
    public Object convert(Object obj) {
        return obj;
    }

    @Override
    public Object convertBack(Object obj) {
        return obj;
    }
}
