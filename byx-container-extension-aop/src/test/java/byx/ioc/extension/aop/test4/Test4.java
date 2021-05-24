package byx.ioc.extension.aop.test4;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertSame;

/**
 * 两个对象构成的循环依赖，其中一个为增强对象
 */
public class Test4 {
    @Test
    public void test1() {
        Container container = new AnnotationConfigContainer(Test4.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        A a = container.getObject(A.class);
        B b = container.getObject(B.class);
        A aa = b.getA();
        B bb = a.getB();

        assertSame(a, aa);
        assertSame(b, bb);
        assertEquals(2003, a.f());
        assertEquals(2003, aa.f());
    }

    @Test
    public void test2() {
        Container container = new AnnotationConfigContainer(Test4.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        B b = container.getObject(B.class);
        A a = container.getObject(A.class);
        A aa = b.getA();
        B bb = a.getB();

        assertSame(a, aa);
        assertSame(b, bb);
        assertEquals(2003, a.f());
        assertEquals(2003, aa.f());
    }
}
