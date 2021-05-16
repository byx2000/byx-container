package byx.ioc.core.test2;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;
import byx.ioc.core.Counter;

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
