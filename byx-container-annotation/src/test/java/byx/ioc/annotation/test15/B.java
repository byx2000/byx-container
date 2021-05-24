package byx.ioc.annotation.test15;

import byx.ioc.annotation.annotation.Component;

@Component
public class B {
    @Component
    public Y y(X x) {
        return new Y();
    }
}
