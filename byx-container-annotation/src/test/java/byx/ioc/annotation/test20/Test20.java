package byx.ioc.annotation.test20;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test20 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test20.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        MyClass myClass = container.getObject(MyClass.class);
        A a = container.getObject(A.class);
        assertNotNull(a);
        assertSame(a, myClass.getA());
    }
}
