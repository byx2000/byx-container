package byx.ioc.annotation.container.test11;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    @Autowired
    private B b;

    @Autowired
    private C c;

    public B getB() {
        return b;
    }

    public C getC() {
        return c;
    }
}
