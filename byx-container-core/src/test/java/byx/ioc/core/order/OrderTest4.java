package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;

public class OrderTest4 {
    @Order(200)
    private static class A {

    }

    @Order(before = {A.class, C.class})
    private static class B {

    }

    @Order(100)
    private static class C {

    }

    @Order(after = {A.class, C.class})
    private static class D {
        
    }

    @Test
    public void test() {
        List<Class<?>> objects = OrderUtils.sort(Arrays.asList(A.class, B.class, C.class, D.class));
        assertEquals(Arrays.asList(D.class, C.class, A.class, B.class), objects);
    }
}
