package byx.ioc.annotation.test12;

import byx.ioc.annotation.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class C {
    @Autowired
    private A a;

    @Autowired
    private B b;

    private final D d;

    public C(D d) {
        Counter.c3++;
        this.d = d;
    }

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }

    public D getD() {
        return d;
    }
}
