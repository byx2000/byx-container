package byx.ioc.extension.aop.test7;

import byx.ioc.extension.aop.annotation.ImplementedBy;

@ImplementedBy(MyInterfaceImpl.class)
public interface MyInterface {
    void f();
    int g(String s);
}
