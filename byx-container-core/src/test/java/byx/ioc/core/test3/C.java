package byx.ioc.core.test3;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;

@Component
public class C {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }
}
