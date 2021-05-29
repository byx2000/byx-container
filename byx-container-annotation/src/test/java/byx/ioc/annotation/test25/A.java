package byx.ioc.annotation.test25;

import byx.ioc.annotation.annotation.Component;

public class A {
    @Component
    public String msg() {
        return "hello";
    }

    protected Integer getValue() {
        return 123;
    }
}
