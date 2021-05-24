package byx.ioc.annotation.test8;

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
