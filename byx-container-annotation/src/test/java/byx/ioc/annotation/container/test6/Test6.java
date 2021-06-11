package byx.ioc.annotation.container.test6;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.annotation.container.test6.x.A;
import byx.ioc.annotation.container.test6.x.B;
import byx.ioc.core.Container;
import byx.ioc.annotation.exception.ConstructorMultiDefException;
import byx.ioc.annotation.exception.ConstructorNotFoundException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test6 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer("byx.ioc.annotation.container.test6.x");
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);

        assertNotNull(b);
        assertSame(a.getB(), b);

        assertThrows(ConstructorNotFoundException.class, () -> new AnnotationConfigContainer("byx.ioc.annotation.container.test6.y"));
        assertThrows(ConstructorMultiDefException.class, () -> new AnnotationConfigContainer("byx.ioc.annotation.container.test6.z"));
    }
}
