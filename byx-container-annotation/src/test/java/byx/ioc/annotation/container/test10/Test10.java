package byx.ioc.annotation.container.test10;

import byx.ioc.annotation.core.AnnotationConfigContainer;
import byx.ioc.annotation.container.test10.dao.UserDao;
import byx.ioc.annotation.container.test10.dao.impl.UserDaoImpl;
import byx.ioc.annotation.container.test10.service.UserService;
import byx.ioc.annotation.container.test10.service.impl.UserServiceImpl;
import byx.ioc.core.Container;
import org.junit.jupiter.api.Test;
import static org.junit.jupiter.api.Assertions.*;

public class Test10 {
    @Test
    public void test() {
        Container container = new AnnotationConfigContainer(Test10.class);
        for (String id : container.getObjectIds()) {
            System.out.println(id);
        }

        UserService userService = container.getObject(UserService.class);
        UserDao userDao = container.getObject(UserDao.class);

        assertTrue(userService instanceof UserServiceImpl);
        assertTrue(userDao instanceof UserDaoImpl);
        assertSame(((UserServiceImpl) userService).getUserDao(), userDao);
    }
}
