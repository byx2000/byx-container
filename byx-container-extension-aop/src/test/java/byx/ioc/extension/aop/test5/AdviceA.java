package byx.ioc.extension.aop.test5;

import byx.aop.annotation.After;
import byx.aop.annotation.Filter;
import byx.ioc.annotation.annotation.Component;

@Component
public class AdviceA {
    @After @Filter(name = "f")
    public int after(int retVal) {
        return retVal + 1;
    }
}
