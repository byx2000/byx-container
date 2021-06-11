package byx.ioc.annotation.container.test5;

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
