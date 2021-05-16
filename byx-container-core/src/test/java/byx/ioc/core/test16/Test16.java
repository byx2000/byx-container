package byx.ioc.core.test16;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import byx.ioc.exception.IdNotFoundException;
import byx.ioc.exception.MultiTypeMatchException;
import byx.ioc.exception.TypeNotFoundException;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

/**
 * setter注入
 */
public class Test16 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test16.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        User user = container.getObject(User.class);

        assertEquals(1001, user.getId());
        assertEquals("byx", user.getUsername());
        assertEquals("123", user.getPassword());

        assertThrows(MultiTypeMatchException.class, () -> container.getObject(A.class));
        assertThrows(TypeNotFoundException.class, () -> container.getObject(B.class));
        assertThrows(IdNotFoundException.class, () -> container.getObject(C.class));

        UserWrapper userWrapper = container.getObject(UserWrapper.class);
        assertSame(userWrapper.getUser(), user);
    }
}
