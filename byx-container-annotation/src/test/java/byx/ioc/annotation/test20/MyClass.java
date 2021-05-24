package byx.ioc.annotation.test20;

public class MyClass {
    private final A a;

    public MyClass(A a) {
        this.a = a;
    }

    public A getA() {
        return a;
    }
}
