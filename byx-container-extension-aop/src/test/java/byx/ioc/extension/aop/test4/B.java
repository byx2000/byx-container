package byx.ioc.extension.aop.test4;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }
}
