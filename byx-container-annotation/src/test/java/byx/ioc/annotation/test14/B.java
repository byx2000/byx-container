package byx.ioc.annotation.test14;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component
public class B {
    @Component
    public String v3(@Id("v2") String s, int i) {
        return s + " " + i;
    }
}
