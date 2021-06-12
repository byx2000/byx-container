package byx.ioc.core.util;

import byx.ioc.util.AnnotationUtils;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.lang.annotation.*;
import java.util.Set;

public class AnnotationUtilsTest2 {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.TYPE)
    private @interface Anno1 {

    }

    @Retention(RetentionPolicy.RUNTIME)
    @Target({ElementType.TYPE, ElementType.METHOD})
    private @interface Anno2 {
        String value();
    }

    @Anno1
    @Anno2("hello")
    private static class A {

    }

    @Test
    public void test1() {
        Set<Annotation> annotations = AnnotationUtils.getAnnotations(A.class);
        for (Annotation a : annotations) {
            System.out.println(a);
        }

        assertEquals(7, annotations.size());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Anno1).count());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Anno2).count());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Documented).count());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Retention).count());
        assertEquals(3, annotations.stream().filter(a -> a instanceof Target).count());

        assertEquals("hello", annotations.stream().filter(a -> a instanceof Anno2).map(a -> ((Anno2) a).value()).toArray()[0]);
    }

    @Test
    public void test2() {
        Set<Class<? extends Annotation>> types = AnnotationUtils.getAnnotationTypes(A.class);
        assertEquals(Set.of(Anno1.class, Anno2.class, Documented.class, Retention.class, Target.class), types);
    }

    @Test
    public void test3() {
        assertTrue(AnnotationUtils.hasAnnotation(A.class, Anno1.class));
        assertTrue(AnnotationUtils.hasAnnotation(A.class, Anno2.class));
        assertTrue(AnnotationUtils.hasAnnotation(A.class, Documented.class));
        assertTrue(AnnotationUtils.hasAnnotation(A.class, Retention.class));
        assertTrue(AnnotationUtils.hasAnnotation(A.class, Target.class));
        assertFalse(AnnotationUtils.hasAnnotation(A.class, Test.class));
    }
}
