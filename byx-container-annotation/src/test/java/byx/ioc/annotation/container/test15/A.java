package byx.ioc.annotation.container.test15;

import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    @Component
    public X x(Y y) {
        return new X();
    }
}
