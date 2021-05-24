package byx.ioc.extension.transaction.test2;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.exception.CannotRegisterInterfaceException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test2 {
    @Test
    public void test() {
        assertThrows(CannotRegisterInterfaceException.class, () -> new AnnotationConfigContainer(Test2.class));
    }
}
