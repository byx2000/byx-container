package byx.ioc.annotation.test23;

import byx.ioc.annotation.Counter;
import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test23 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test23.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        Counter.c1 = Counter.c2 = Counter.c3 = 0;
        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        C c = container.getObject(C.class);

        assertSame(a.getB(), b);
        assertSame(a.getC(), c);
        assertSame(b.getA(), a);
        assertSame(c.getA(), a);

        assertEquals(1, Counter.c1);
        assertEquals(1, Counter.c2);
        assertEquals(1, Counter.c3);
    }
}
