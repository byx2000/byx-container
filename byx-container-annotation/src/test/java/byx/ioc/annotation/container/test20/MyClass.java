package byx.ioc.annotation.container.test20;

import byx.ioc.annotation.annotation.Component;

@Component
public class MyClass {
    private final A a;

    public MyClass(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }
}
