package byx.ioc.annotation.test5;

import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    private final C c;

    public B(C c) {
        this.c = c;
    }

    public C getC() {
        return c;
    }
}
