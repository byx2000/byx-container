package byx.ioc.core.test23;

import byx.ioc.annotation.Component;
import byx.ioc.core.Counter;

@Component
public class A {
    private final B b;
    private final C c;

    public A(B b, C c) {
        this.b = b;
        this.c = c;
        Counter.c1++;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }
}
