package byx.ioc.core.test19;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test19 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test19.class);

        A a = container.getObject("a");
        A a1 = container.getObject("a1");
        A a2 = container.getObject("a2");

        assertEquals(0, a.getVal());
        assertEquals(1, a1.getVal());
        assertEquals(2, a2.getVal());
    }
}
