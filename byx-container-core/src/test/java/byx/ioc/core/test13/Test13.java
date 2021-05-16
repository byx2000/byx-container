package byx.ioc.core.test13;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.exception.CircularDependencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test13 {
    @Test
    public void test() {
        assertThrows(CircularDependencyException.class, () -> new AnnotationConfigContainer(Test13.class));
    }
}
