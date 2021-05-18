package byx.ioc.extension.aop.test7;

import byx.ioc.annotation.Component;
import byx.ioc.extension.aop.Flag;

@Component
public class MyInterfaceImpl {
    public void f() {
        System.out.println("f");
        Flag.f1 = true;
    }

    public int g(String s) {
        return s.length();
    }
}
