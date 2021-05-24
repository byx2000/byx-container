package byx.ioc.annotation.test4;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component
public class A {
    @Autowired
    @Id("b1")
    private B b;

    public B getB() {
        return b;
    }
}
