package byx.ioc.core.test23;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;
import byx.ioc.core.Counter;

@Component
public class C {
    @Autowired
    private A a;

    public C() {
        Counter.c3++;
    }

    public A getA() {
        return a;
    }
}
