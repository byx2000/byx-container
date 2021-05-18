package byx.ioc.core.test21;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test21 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test21.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        assertNull(container.getObject(MyInterface.class));
    }
}
