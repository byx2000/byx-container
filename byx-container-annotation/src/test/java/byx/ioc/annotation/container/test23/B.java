package byx.ioc.annotation.container.test23;

import byx.ioc.annotation.container.Counter;
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
