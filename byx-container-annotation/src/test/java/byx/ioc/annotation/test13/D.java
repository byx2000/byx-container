package byx.ioc.annotation.test13;

import byx.ioc.annotation.annotation.Component;

@Component
public class D {
    private final A a;

    public D(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }
}
