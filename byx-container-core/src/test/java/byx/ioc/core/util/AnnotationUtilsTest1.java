package byx.ioc.core.util;

import byx.ioc.util.AnnotationUtils;
import org.junit.jupiter.api.Test;

import java.lang.annotation.Annotation;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.*;

public class AnnotationUtilsTest1 {
    private static class A {
    }

    @Test
    public void test1() {
        Set<Annotation> annotations = AnnotationUtils.getAnnotations(A.class);
        assertTrue(annotations.isEmpty());
    }

    @Test
    public void test2() {
        Set<Class<? extends Annotation>> types = AnnotationUtils.getAnnotationTypes(A.class);
        assertTrue(types.isEmpty());
    }

    @Test
    public void test3() {
        assertFalse(AnnotationUtils.hasAnnotation(A.class, Test.class));
    }
}
