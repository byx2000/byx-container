package byx.ioc.core.test11;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;

@Component
public class B {
    @Autowired
    private A a;

    @Autowired
    private C c;

    public A getA() {
        return a;
    }

    public C getC() {
        return c;
    }
}
