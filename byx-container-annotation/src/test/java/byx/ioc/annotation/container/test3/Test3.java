package byx.ioc.annotation.container.test3;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertSame;

public class Test3 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test3.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        C c = container.getObject(C.class);

        assertSame(a.getB(), b);
        assertSame(b.getC(), c);
        assertSame(c.getA(), a);

        X x = container.getObject(X.class);
        Y y = container.getObject(Y.class);
        Z z = container.getObject(Z.class);

        assertSame(x.getY(), y);
        assertSame(y.getZ(), z);
        assertSame(z.getX(), x);
    }
}
