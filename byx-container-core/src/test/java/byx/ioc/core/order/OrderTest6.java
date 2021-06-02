package byx.ioc.core.order;

import byx.ioc.annotation.Order;
import byx.ioc.exception.CircularOrderException;
import byx.ioc.util.OrderUtils;
import org.junit.jupiter.api.Test;

import java.util.Arrays;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class OrderTest6 {
    @Order(before = B.class)
    public static class A {

    }

    @Order(before = C.class)
    public static class B {

    }

    @Order(before = A.class)
    public static class C {

    }

    @Order(after = A.class)
    public static class D {

    }

    @Test
    public void test() {
        assertThrows(CircularOrderException.class, () -> OrderUtils.sort(Arrays.asList(A.class, B.class, C.class, D.class)));
    }
}
