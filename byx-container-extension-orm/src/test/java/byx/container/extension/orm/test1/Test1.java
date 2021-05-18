package byx.container.extension.orm.test1;

import byx.ioc.core.AnnotationConfigContainer;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

import java.util.List;

public class Test1 {
    @Test
    public void test1() {
        Container container = new AnnotationConfigContainer(Test1.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        UserDao userDao = container.getObject(UserDao.class);

        List<User> users = userDao.listAll();
        for (User u : users) {
            System.out.println(u);
        }
        assertEquals(3, users.size());
        assertEquals(1, users.get(0).getId());
        assertEquals("aaa", users.get(0).getUsername());
        assertEquals("123", users.get(0).getPassword());
        assertEquals(2, users.get(1).getId());
        assertEquals("bbb", users.get(1).getUsername());
        assertEquals("456", users.get(1).getPassword());
        assertEquals(3, users.get(2).getId());
        assertEquals("ccc", users.get(2).getUsername());
        assertEquals("789", users.get(2).getPassword());
    }

    @Test
    public void test2() {
        Container container = new AnnotationConfigContainer(Test1.class);
        UserDao userDao = container.getObject(UserDao.class);

        User user = userDao.getById(2);
        System.out.println(user);
        assertEquals(2, user.getId());
        assertEquals("bbb", user.getUsername());
        assertEquals("456", user.getPassword());
    }

    @Test
    public void test3() {
        Container container = new AnnotationConfigContainer(Test1.class);
        UserDao userDao = container.getObject(UserDao.class);

        assertEquals(3, userDao.count());

        User user = new User();
        user.setUsername("byx");
        user.setPassword("666");
        assertEquals(1, userDao.insert(user));

        assertEquals(4, userDao.count());

        userDao.delete("byx", "666");
        assertEquals(3, userDao.count());
    }
}
