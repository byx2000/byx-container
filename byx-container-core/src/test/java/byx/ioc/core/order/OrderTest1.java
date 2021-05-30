package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class OrderTest1 {
    @Order(2)
    private static class A {

    }

    @Order
    private static class B {

    }

    @Order(3)
    private static class C {

    }

    @Test
    public void test() {
        A a = new A();
        B b = new B();
        C c = new C();
        List<Object> objects = OrderUtils.sort(Arrays.asList(a, b, c));
        assertEquals(Arrays.asList(b, a, c), objects);
    }
}
