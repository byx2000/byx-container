package byx.ioc.annotation.test16;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component
public class C {
    @Autowired
    public void set(@Id("msg") String s) {

    }
}
