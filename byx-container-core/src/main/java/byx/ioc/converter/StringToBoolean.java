package byx.ioc.converter;

import byx.ioc.core.ValueConverter;

/**
 * String -> boolean
 *
 * @author byx
 */
public class StringToBoolean implements ValueConverter {
    @Override
    public Class<?> fromType() {
        return String.class;
    }

    @Override
    public Class<?> toType() {
        return boolean.class;
    }

    @Override
    public Object convert(Object obj) {
        return Boolean.parseBoolean((String) obj);
    }

    @Override
    public Object convertBack(Object obj) {
        return obj.toString();
    }
}
