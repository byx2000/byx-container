package byx.ioc.annotation.test3;

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
