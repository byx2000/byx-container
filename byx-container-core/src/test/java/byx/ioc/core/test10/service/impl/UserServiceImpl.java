package byx.ioc.core.test10.service.impl;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;
import byx.ioc.core.test10.dao.UserDao;
import byx.ioc.core.test10.service.UserService;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }
}
