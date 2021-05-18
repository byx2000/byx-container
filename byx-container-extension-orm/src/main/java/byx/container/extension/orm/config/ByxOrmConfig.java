package byx.container.extension.orm.config;

import byx.ioc.annotation.Component;
import byx.orm.DaoGenerator;
import byx.util.jdbc.JdbcUtils;

/**
 * ByxOrm配置类
 *
 * @author byx
 */
public class ByxOrmConfig {
    @Component
    public DaoGenerator daoGenerator(JdbcUtils jdbcUtils) {
        return new DaoGenerator(jdbcUtils);
    }
}
