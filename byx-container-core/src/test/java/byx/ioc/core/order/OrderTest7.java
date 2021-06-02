package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.Arrays;
import java.util.List;

public class OrderTest7 {
    @Order(before = {String.class, B.class})
    private static class A {

    }

    @Order(after = Integer.class)
    private static class B {

    }

    @Test
    public void test() {
        List<Class<?>> objects = OrderUtils.sort(Arrays.asList(A.class, B.class));
        assertEquals(Arrays.asList(B.class, A.class), objects);
    }
}
