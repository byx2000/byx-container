package byx.ioc.annotation.test8;

import byx.ioc.annotation.annotation.Component;

@Component
public class C {
    private final D d;

    public C(D d) {
        this.d = d;
    }

    public D getD() {
        return d;
    }
}
