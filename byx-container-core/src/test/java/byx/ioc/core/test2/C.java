package byx.ioc.core.test2;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;
import byx.ioc.core.Counter;

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
