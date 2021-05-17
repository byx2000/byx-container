package byx.ioc.extension.aop.test5;

import byx.ioc.annotation.Autowired;
import byx.ioc.extension.aop.annotation.AdviceBy;

@AdviceBy(AdviceA.class)
public class A {
    @Autowired
    private B b;

    public B getB() {
        return b;
    }

    public int f() {
        return 100;
    }
}
