package byx.ioc.annotation.container.test3;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    @Autowired
    public C c;

    public C getC() {
        return c;
    }
}
