package byx.ioc.annotation.container.test18;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * 使用Value注解注入常量值
 */
public class Test18 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test18.class);

        System.out.println((String) container.getObject("strVal"));
        System.out.println(container.getObject(int.class));
        System.out.println((double) container.getObject("doubleVal"));
        System.out.println(container.getObject(char.class));
        System.out.println(container.getObject(boolean.class));
        System.out.println((String) container.getObject("hi"));
        System.out.println((double) container.getObject("6.28"));
        System.out.println(container.getObject(User.class));

        assertEquals("hello", container.getObject("strVal"));
        assertEquals(123, container.getObject(int.class));
        assertEquals(3.14, container.getObject("doubleVal"));
        assertEquals('a', container.getObject(char.class));
        assertEquals(true, container.getObject(boolean.class));
        assertEquals("hi", container.getObject("hi"));
        assertEquals(6.28, container.getObject("6.28"));
        User user = container.getObject(User.class);
        assertEquals(1001, user.getId());
        assertEquals("byx", user.getUsername());
        assertEquals("123", user.getPassword());
    }
}
