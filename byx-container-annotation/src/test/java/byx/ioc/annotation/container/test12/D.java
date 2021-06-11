package byx.ioc.annotation.container.test12;

import byx.ioc.annotation.container.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class D {
    @Autowired
    private A a;

    @Autowired
    private B b;

    @Autowired
    private C c;

    public D() {
        Counter.c4++;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }
}
