package byx.ioc.extension.transaction.test2;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test2 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test2.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        MyService myService = container.getObject(MyService.class);
        assertNull(myService);
    }
}
