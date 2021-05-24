package byx.ioc.annotation.test5;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class C {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }
}
