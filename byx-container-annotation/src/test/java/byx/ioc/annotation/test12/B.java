package byx.ioc.annotation.test12;

import byx.ioc.annotation.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    @Autowired
    private A a;

    private final C c;
    private final D d;

    public B(C c, D d) {
        Counter.c2++;
        this.c = c;
        this.d = d;
    }

    public A getA() {
        return a;
    }

    public C getC() {
        return c;
    }

    public D getD() {
        return d;
    }
}
