package byx.ioc.annotation.container.test8;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class D {
    @Autowired
    private E e;

    public E getE() {
        return e;
    }
}
