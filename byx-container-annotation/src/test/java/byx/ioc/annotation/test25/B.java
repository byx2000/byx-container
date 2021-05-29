package byx.ioc.annotation.test25;

import byx.ioc.annotation.annotation.Component;

@Component
public class B extends A {
    @Component
    public Integer value() {
        return getValue();
    }
}
