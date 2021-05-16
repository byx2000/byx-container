package byx.ioc.core.test15;

import byx.ioc.annotation.Component;

@Component
public class B {
    @Component
    public Y y(X x) {
        return new Y();
    }
}
