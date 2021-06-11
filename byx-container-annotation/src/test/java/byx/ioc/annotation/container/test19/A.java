package byx.ioc.annotation.container.test19;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("a")
public class A {
    private final int val;

    @Autowired
    public A() {
        val = 0;
    }

    public A(int val) {
        this.val = val;
    }

    public int getVal() {
        return val;
    }

    @Component
    public A a1() {
        return new A(1);
    }

    @Component
    public A a2() {
        return new A(2);
    }
}
