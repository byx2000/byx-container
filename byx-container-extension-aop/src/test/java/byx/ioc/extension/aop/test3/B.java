package byx.ioc.extension.aop.test3;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;

@Component
public class B {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }
}
