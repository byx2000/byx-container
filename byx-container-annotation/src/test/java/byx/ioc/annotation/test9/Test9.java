package byx.ioc.annotation.test9;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class Test9 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test9.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);

        assertSame(a.getA(), a);
        assertSame(a.getB(), b);
        assertSame(b.getA(), a);
        assertSame(b.getB(), b);
    }
}
