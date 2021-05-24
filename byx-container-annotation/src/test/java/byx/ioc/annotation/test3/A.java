package byx.ioc.annotation.test3;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    @Autowired
    private B b;

    public B getB() {
        return b;
    }
}
