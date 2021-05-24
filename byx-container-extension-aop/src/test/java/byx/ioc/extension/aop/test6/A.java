package byx.ioc.extension.aop.test6;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.extension.aop.annotation.AdvisedBy;

@AdvisedBy(AdviceA.class)
public class A {
    private B b;

    public A() {}

    @Autowired
    public A(B b) {
        this.b = b;
    }

    public B getB() {
        return b;
    }

    public int f() {
        return 100;
    }
}
