package byx.ioc.annotation.test17;

import byx.ioc.annotation.State;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Init;

@Component
public class B {
    @Init
    public void init(String s) {
        System.out.println("init: " + s);
        State.state += s;
    }
}
