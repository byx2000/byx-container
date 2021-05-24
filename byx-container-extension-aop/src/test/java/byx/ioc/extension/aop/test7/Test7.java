package byx.ioc.extension.aop.test7;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import byx.ioc.extension.aop.Flag;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test7 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test7.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        MyInterface m = container.getObject(MyInterface.class);

        Flag.f1 = false;
        m.f();
        assertTrue(Flag.f1);

        assertEquals(5, m.g("hello"));
    }
}
