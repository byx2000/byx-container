package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class OrderTest5 {
    @Order(before = {B.class, C.class})
    private static class A {

    }

    @Order(before = C.class)
    private static class B {

    }

    private static class C {

    }

    @Test
    public void test() {
        List<Class<?>> objects = OrderUtils.sort(Arrays.asList(A.class, B.class, C.class));
        assertEquals(Arrays.asList(C.class, B.class, A.class), objects);
    }
}
