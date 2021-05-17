package byx.ioc.extension.aop.test2;

import byx.ioc.annotation.Autowired;
import byx.ioc.extension.aop.annotation.AdviceBy;

@AdviceBy(Advice.class)
public class A {
    @Autowired
    private A a;

    public int f() {
        return 888;
    }

    public A getA() {
        return a;
    }
}
