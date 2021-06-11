package byx.ioc.annotation.container.test17;

import byx.ioc.annotation.container.State;
import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import byx.ioc.annotation.exception.MultiInitMethodDefException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

/**
 * 使用Init注解指定初始化方法
 */
public class Test17 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test17.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        State.state = "";
        A a = container.getObject(A.class);
        assertEquals("cssi", State.state);

        State.state = "";
        B b = container.getObject(B.class);
        assertEquals("hello", State.state);

        assertThrows(MultiInitMethodDefException.class, () -> container.getObject(C.class));
    }
}
