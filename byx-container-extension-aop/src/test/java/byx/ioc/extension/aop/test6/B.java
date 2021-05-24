package byx.ioc.extension.aop.test6;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.extension.aop.annotation.AdvisedBy;

@AdvisedBy(AdviceB.class)
public class B {
    @Autowired
    private A a;

    public A getA() {
        return a;
    }

    public int g() {
        return 200;
    }
}
