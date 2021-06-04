package byx.container.extension.orm.config;

import byx.ioc.annotation.annotation.Component;
import byx.orm.core.DaoGenerator;
import byx.util.jdbc.JdbcUtils;

/**
 * ByxOrm配置类
 *
 * @author byx
 */
@Component
public class ByxOrmConfig {
    @Component
    public DaoGenerator daoGenerator(JdbcUtils jdbcUtils) {
        return new DaoGenerator(jdbcUtils);
    }
}
