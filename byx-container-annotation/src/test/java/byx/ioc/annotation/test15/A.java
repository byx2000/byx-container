package byx.ioc.annotation.test15;

import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    @Component
    public X x(Y y) {
        return new X();
    }
}
