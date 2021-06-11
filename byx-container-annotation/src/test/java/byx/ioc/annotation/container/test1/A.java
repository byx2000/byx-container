package byx.ioc.annotation.container.test1;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;
import byx.ioc.annotation.container.test1.x.B;

@Component
public class A {
    private final B b;

    public A(@Id("b1") B b) {
        this.b = b;
    }

    public B getB() {
        return b;
    }

    @Component
    public String message() {
        return "hello";
    }

    @Component
    public String s2(Double d, @Id("info") String s) {
        return d + " " + s;
    }
}
