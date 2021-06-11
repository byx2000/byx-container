package byx.ioc.annotation.container.test13;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.exception.CircularDependencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

public class Test13 {
    @Test
    public void test() {
        assertThrows(CircularDependencyException.class, () -> new AnnotationConfigContainer(Test13.class));
    }
}
