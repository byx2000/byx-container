package byx.ioc.annotation.container.test3;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("y")
public class Y {
    @Autowired
    @Id("z")
    private Z z;

    public Z getZ() {
        return z;
    }
}
