package byx.ioc.extension.aop.test3;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * 注入增强对象，无循环依赖
 */
public class Test3 {
    @Test
    public void test1() {
        Container container = new AnnotationConfigContainer(Test3.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        A aa = b.getA();

        assertSame(a, aa);
        assertEquals(1002, a.f());
        assertEquals(1002, aa.f());
    }

    @Test
    public void test2() {
        Container container = new AnnotationConfigContainer(Test3.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        B b = container.getObject(B.class);
        A aa = b.getA();
        A a = container.getObject(A.class);

        assertSame(a, aa);
        assertEquals(1002, a.f());
        assertEquals(1002, aa.f());
    }
}
