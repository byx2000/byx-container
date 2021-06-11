package byx.ioc.annotation.container.test13;

import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    private final C c;
    private final D d;

    public B(C c, D d) {
        this.c = c;
        this.d = d;
    }

    public C getC() {
        return c;
    }

    public D getD() {
        return d;
    }
}
