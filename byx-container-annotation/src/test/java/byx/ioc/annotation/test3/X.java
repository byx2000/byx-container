package byx.ioc.annotation.test3;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("x")
public class X {
    @Autowired
    @Id("y")
    private Y y;

    public Y getY() {
        return y;
    }
}
