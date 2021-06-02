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
        List<Class<?>> objects = OrderUtils.sort(Arrays.asList(A.class, B.class, C.class, D.class));
        assertEquals(Arrays.asList(B.class, C.class, A.class, D.class), objects);
    }
}
