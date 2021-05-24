package byx.ioc.annotation.test18;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.core.ValueConverter;

import java.util.Arrays;

@Component
public class UserConverter implements ValueConverter {
    @Override
    public Class<?> fromType() {
        return String.class;
    }

    @Override
    public Class<?> toType() {
        return User.class;
    }

    @Override
    public Object convert(Object obj) {
        String s = (String) obj;
        s = s.substring(5, s.length() - 1);
        System.out.println(s);
        String[] ps = s.split(",");
        System.out.println(Arrays.toString(ps));
        return new User(Integer.valueOf(ps[0]), ps[1].substring(1, ps[1].length() - 1), ps[2].substring(1, ps[2].length() - 1));
    }

    @Override
    public Object convertBack(Object obj) {
        throw new UnsupportedOperationException();
    }
}
