package byx.ioc.annotation.test24;

import byx.ioc.annotation.State;
import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test24 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test24.class);

        State.state = "";
        A a = container.getObject(A.class);
        assertEquals("hello", State.state);
    }
}
