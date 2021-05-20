package byx.ioc.core.test22;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test22 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test22.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        C c = container.getObject(C.class);
        assertNotNull(a);
        assertNotNull(b);
        assertNotNull(c);
    }
}
