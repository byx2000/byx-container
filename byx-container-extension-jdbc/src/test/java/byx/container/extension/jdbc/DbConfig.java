package byx.container.extension.jdbc;

import byx.ioc.annotation.Component;
import com.alibaba.druid.pool.DruidDataSource;

import javax.sql.DataSource;

@Component
public class DbConfig {
    @Component
    public DataSource dataSource() {
        DruidDataSource dataSource = new DruidDataSource();
        dataSource.setDriverClassName("org.sqlite.JDBC");
        dataSource.setUrl("jdbc:sqlite::resource:test.db");
        dataSource.setUsername("");
        dataSource.setPassword("");
        dataSource.setTestWhileIdle(false);
        return dataSource;
    }
}
