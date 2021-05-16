package byx.ioc.core.test2;

import byx.ioc.annotation.Component;
import byx.ioc.core.Counter;

@Component
public class X {
    private final Y y;

    public X(Y y) {
        Counter.c4++;
        this.y = y;
    }

    public Y getY() {
        return y;
    }
}
