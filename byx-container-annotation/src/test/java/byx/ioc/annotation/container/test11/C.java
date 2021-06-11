package byx.ioc.annotation.container.test11;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class C {
    @Autowired
    private A a;

    @Autowired
    private B b;

    public A getA() {
        return a;
    }

    public B getB() {
        return b;
    }
}
