package byx.ioc.annotation.test4;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertNotNull;
import static org.junit.jupiter.api.Assertions.assertSame;

public class Test4 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer("byx.ioc.annotation.test4");
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B1 b1 = container.getObject(B1.class);
        assertNotNull(b1);
        assertSame(a.getB(), b1);
    }
}
