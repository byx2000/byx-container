package byx.ioc.core.test11;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class Test11 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test11.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        C c = container.getObject(C.class);

        assertSame(a.getB(), b);
        assertSame(a.getC(), c);
        assertSame(b.getA(), a);
        assertSame(b.getC(), c);
        assertSame(c.getA(), a);
        assertSame(c.getB(), b);
    }
}
