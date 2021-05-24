package byx.ioc.annotation.test8;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    @Autowired
    private C c;

    public C getC() {
        return c;
    }
}
