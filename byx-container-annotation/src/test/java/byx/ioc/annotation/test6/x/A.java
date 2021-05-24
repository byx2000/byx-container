package byx.ioc.annotation.test6.x;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    private B b;

    public A() {

    }

    @Autowired
    public A(B b) {
        this.b = b;
    }

    public B getB() {
        return b;
    }
}
