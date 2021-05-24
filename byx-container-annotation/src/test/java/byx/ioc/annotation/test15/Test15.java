package byx.ioc.annotation.test15;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.exception.CircularDependencyException;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertThrows;

/**
 * 方法注入构成的循环依赖
 */
public class Test15 {
    @Test
    public void test() {
        assertThrows(CircularDependencyException.class, () -> new AnnotationConfigContainer(Test15.class));
    }
}
