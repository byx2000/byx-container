package byx.ioc.extension.transaction.test1;

import byx.ioc.annotation.Autowired;
import byx.ioc.annotation.Component;
import byx.util.jdbc.JdbcUtils;

@Component
public class MyDao {
    @Autowired
    private JdbcUtils jdbcUtils;

    public JdbcUtils getJdbcUtils() {
        return jdbcUtils;
    }

    public void dao1() {
        jdbcUtils.update("update A set value = 90");
    }

    public void dao2() {
        jdbcUtils.update("update B set value = 10");
    }
}
