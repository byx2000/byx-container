package byx.ioc.annotation.container.test3;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("z")
public class Z {
    @Autowired
    @Id("x")
    private X x;

    public X getX() {
        return x;
    }
}
