package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class OrderTest2 {
    private interface MyInterface {

    }

    @Order(before = C.class, after = D.class)
    private static class A implements MyInterface {

    }

    private static class B implements MyInterface {

    }

    @Order(before = B.class)
    private static class C implements MyInterface {

    }

    private static class D implements MyInterface {

    }

    @Test
    public void test() {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        List<MyInterface> objects = OrderUtils.sort(Arrays.asList(a, b, c, d));
        assertEquals(Arrays.asList(b, c, a, d), objects);
    }
}
