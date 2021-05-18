package byx.ioc.extension.aop.test3;

import byx.ioc.extension.aop.annotation.AdvisedBy;

@AdvisedBy(Advice.class)
public class A {
    public int f() {
        return 1001;
    }
}
