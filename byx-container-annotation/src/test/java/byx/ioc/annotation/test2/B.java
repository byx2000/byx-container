package byx.ioc.annotation.test2;

import byx.ioc.annotation.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    @Autowired
    private A a;

    public B() {
        Counter.c2++;
    }

    public A getA() {
        return a;
    }
}
