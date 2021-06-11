package byx.ioc.annotation.container.test2;

import byx.ioc.annotation.container.Counter;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class Y {
    @Autowired
    private X x;

    public Y() {
        Counter.c5++;
    }

    public X getX() {
        return x;
    }
}
