package byx.ioc.annotation.container.test1.x;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("b2")
public class B2 extends B {
    @Override
    public String toString() {
        return "b2";
    }

    @Component
    public Integer lengthOfMessage(@Id("message") String message) {
        return message.length();
    }
}
