package byx.ioc.annotation.test14;

import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;

@Component @Id("c")
public class C {
    public int getVal() {
        return 12345;
    }

    @Component
    public C cc(@Id("c") C c) {
        return c;
    }
}
