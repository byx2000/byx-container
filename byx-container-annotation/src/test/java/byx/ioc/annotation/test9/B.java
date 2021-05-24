package byx.ioc.annotation.test9;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class B {
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
