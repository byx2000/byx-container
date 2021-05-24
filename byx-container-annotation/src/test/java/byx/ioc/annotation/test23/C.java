package byx.ioc.annotation.test23;

import byx.ioc.annotation.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

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
