package byx.ioc.annotation.container.test24;

import byx.ioc.annotation.container.State;
import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Init;
import byx.ioc.annotation.annotation.Value;

@Value(id = "msg", value = "hello")
public class A {
    @Autowired
    private String msg;

    public String getMsg() {
        return msg;
    }

    @Init
    public void init() {
        System.out.println("init: " + msg);
        State.state = msg;
    }
}