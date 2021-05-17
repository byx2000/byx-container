package byx.ioc.extension.aop.test3;

import byx.ioc.extension.aop.annotation.AdviceBy;

@AdviceBy(Advice.class)
public class A {
    public int f() {
        return 1001;
    }
}
