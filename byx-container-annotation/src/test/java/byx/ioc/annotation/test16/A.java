package byx.ioc.annotation.test16;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;

@Component
public class A {
    @Autowired
    public void set(String s) {

    }
}
