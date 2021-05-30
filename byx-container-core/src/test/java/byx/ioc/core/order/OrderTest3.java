package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class OrderTest3 {
    @Order(before = D.class, value = 100)
    private static class A {

    }

    @Order(before = {A.class, C.class})
    private static class B {

    }

    @Order(before = D.class, value = 200)
    private static class C {

    }

    private static class D {

    }

    @Test
    public void test() {
        A a = new A();
        B b = new B();
        C c = new C();
        D d = new D();
        List<Object> objects = OrderUtils.sort(Arrays.asList(a, b, c, d));
        assertEquals(Arrays.asList(d, a, c, b), objects);
    }
}
