package byx.ioc.annotation.container.test1.x;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("b1")
public class B1 extends B {
    @Override
    public String toString() {
        return "b1";
    }

    @Component
    public String info() {
        return "hi";
    }

    @Component @Id("pi")
    public Double getPi() {
        return 3.14;
    }
}
