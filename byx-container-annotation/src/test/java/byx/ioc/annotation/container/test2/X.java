package byx.ioc.annotation.container.test2;

import byx.ioc.annotation.container.Counter;
import byx.ioc.annotation.annotation.Component;

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
