package byx.ioc.extension.aop.test5;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * 两个对象构成的循环依赖，两个对象都为增强对象
 */
public class Test5 {
    @Test
    public void test1() {
        Container container = new AnnotationConfigContainer(Test5.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        A aa = b.getA();
        B bb = a.getB();

        assertSame(a, aa);
        assertSame(b, bb);
        assertEquals(101, a.f());
        assertEquals(101, aa.f());
        assertEquals(202, b.g());
        assertEquals(202, bb.g());
    }

    @Test
    public void test2() {
        Container container = new AnnotationConfigContainer(Test5.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B bb = a.getB();
        B b = container.getObject(B.class);
        A aa = b.getA();

        assertSame(a, aa);
        assertSame(b, bb);
        assertEquals(101, a.f());
        assertEquals(101, aa.f());
        assertEquals(202, b.g());
        assertEquals(202, bb.g());
    }
}
