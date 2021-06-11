package byx.ioc.annotation.container.test2;

import byx.ioc.annotation.container.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    @Autowired
    private B b;

    public A() {
        Counter.c1++;
    }

    public B getB() {
        return b;
    }
}
