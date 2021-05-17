package byx.container.extension.jdbc;

import byx.ioc.annotation.Component;
import byx.util.jdbc.JdbcUtils;

import javax.sql.DataSource;

/**
 * 将JdbcUtils注册到容器
 *
 * @author byx
 */
public class JdbcConfig {
    @Component
    public JdbcUtils jdbcUtils(DataSource dataSource) {
        return new JdbcUtils(dataSource);
    }
}
