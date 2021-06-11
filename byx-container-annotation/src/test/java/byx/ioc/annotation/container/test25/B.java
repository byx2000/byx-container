package byx.ioc.annotation.container.test25;

import byx.ioc.annotation.annotation.Component;

@Component
public class B extends A {
    @Component
    public Integer value() {
        return getValue();
    }
}
