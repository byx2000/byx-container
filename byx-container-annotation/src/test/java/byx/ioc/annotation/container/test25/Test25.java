package byx.ioc.annotation.container.test25;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test25 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test25.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        String msg = container.getObject(String.class);
        assertEquals("hello", msg);

        Integer value = container.getObject(Integer.class);
        assertEquals(123, value);
    }
}
