package byx.ioc.extension.aop.test1;

import byx.ioc.extension.aop.annotation.AdvisedBy;

import static org.junit.jupiter.api.Assertions.assertEquals;

@AdvisedBy(Advice.class)
public class A {
    public void f(int n) {
        assertEquals(124, n);
        System.out.println("f");
        System.out.println("n = " + n);
    }

    public void g() {
        System.out.println("g");
    }
}
