package byx.ioc.annotation.container.test6.z;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class D {
    @Autowired
    public D() {

    }

    @Autowired
    public D(String s) {

    }
}
