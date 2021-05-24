package byx.ioc.annotation.test10.service.impl;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.test10.dao.UserDao;
import byx.ioc.annotation.test10.service.UserService;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;

    public UserDao getUserDao() {
        return userDao;
    }
}
