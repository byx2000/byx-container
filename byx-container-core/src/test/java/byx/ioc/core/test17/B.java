package byx.ioc.core.test17;

import byx.ioc.annotation.Component;
import byx.ioc.annotation.Init;
import byx.ioc.core.State;

@Component
public class B {
    @Init
    public void init(String s) {
        System.out.println("init: " + s);
        State.state += s;
    }
}
