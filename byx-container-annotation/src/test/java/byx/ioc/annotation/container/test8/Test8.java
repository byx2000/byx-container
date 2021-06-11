package byx.ioc.annotation.container.test8;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class Test8 {
    @Test
    public void test8() {
        Container container = new AnnotationConfigContainer(Test8.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        C c = container.getObject(C.class);
        D d = container.getObject(D.class);
        E e = container.getObject(E.class);

        assertSame(a.getB(), b);
        assertSame(b.getC(), c);
        assertSame(c.getD(), d);
        assertSame(d.getE(), e);
        assertSame(e.getA(), a);
    }
}
