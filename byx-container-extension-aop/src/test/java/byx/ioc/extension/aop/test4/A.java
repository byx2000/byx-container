package byx.ioc.extension.aop.test4;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.extension.aop.annotation.AdvisedBy;

@AdvisedBy(Advice.class)
public class A {
    @Autowired
    private B b;

    public B getB() {
        return b;
    }

    public int f() {
        return 2002;
    }
}
