package byx.ioc.core.util;

import byx.ioc.util.AnnotationUtils;
import org.junit.jupiter.api.Test;

import java.lang.annotation.*;
import java.lang.reflect.Method;
import java.util.Set;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class AnnotationUtilsTest3 {
    @Retention(RetentionPolicy.RUNTIME)
    @Target(ElementType.METHOD)
    private @interface Anno1 {
        String value();
    }

    @Anno1("hello")
    public void f(String s) {

    }

    @Test
    public void test1() throws Exception {
        Method method = AnnotationUtilsTest3.class.getMethod("f", String.class);
        Set<Annotation> annotations = AnnotationUtils.getAnnotations(method);
        for (Annotation a : annotations) {
            System.out.println(a);
        }

        assertEquals(5, annotations.size());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Anno1).count());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Documented).count());
        assertEquals(1, annotations.stream().filter(a -> a instanceof Retention).count());
        assertEquals(2, annotations.stream().filter(a -> a instanceof Target).count());

        assertEquals("hello", annotations.stream().filter(a -> a instanceof Anno1).map(a -> ((Anno1) a).value()).toArray()[0]);
    }

    @Test
    public void test2() throws Exception {
        Method method = AnnotationUtilsTest3.class.getMethod("f", String.class);
        Set<Class<? extends Annotation>> types = AnnotationUtils.getAnnotationTypes(method);
        assertEquals(Set.of(Anno1.class, Documented.class, Retention.class, Target.class), types);
    }

    @Test
    public void test3() throws Exception {
        Method method = AnnotationUtilsTest3.class.getMethod("f", String.class);
        assertTrue(AnnotationUtils.hasAnnotation(method, Anno1.class));
        assertTrue(AnnotationUtils.hasAnnotation(method, Documented.class));
        assertTrue(AnnotationUtils.hasAnnotation(method, Retention.class));
        assertTrue(AnnotationUtils.hasAnnotation(method, Target.class));
    }
}
