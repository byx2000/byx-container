package byx.ioc.annotation.test2;

import byx.ioc.annotation.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class C {
    @Autowired
    private C c;

    public C() {
        Counter.c3++;
    }

    public C getC() {
        return c;
    }
}
