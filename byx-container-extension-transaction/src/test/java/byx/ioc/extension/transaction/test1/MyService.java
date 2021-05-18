package byx.ioc.extension.transaction.test1;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;
import byx.transaction.annotation.Transactional;
import byx.util.jdbc.JdbcUtils;

@Component
public class MyService {
    @Autowired
    private MyDao myDao;

    public JdbcUtils getJdbcUtils() {
        return myDao.getJdbcUtils();
    }

    @Transactional
    public void service1() {
        myDao.dao1();
        int a = 1 / 0;
        myDao.dao2();
    }

    public void service2() {
        myDao.dao1();
        int a = 1 / 0;
        myDao.dao2();
    }
}
