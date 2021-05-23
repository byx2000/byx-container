package byx.ioc.core.test21;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.exception.CannotRegisterInterfaceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test21 {
    @Test
    public void test() {
        assertThrows(CannotRegisterInterfaceException.class, () -> new AnnotationConfigContainer(Test21.class));
    }
}
