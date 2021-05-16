package byx.ioc.core.test15;

import byx.ioc.annotation.Component;

@Component
public class A {
    @Component
    public X x(Y y) {
        return new X();
    }
}
