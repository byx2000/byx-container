package byx.container.extension.jdbc;

import byx.ioc.core.*;
import byx.util.jdbc.JdbcUtils;

import javax.sql.DataSource;

/**
 * 注册JdbcUtils到容器
 *
 * @author byx
 */
public class JdbcUtilsRegister implements ContainerCallback {
    @Override
    public void afterContainerInit(Container container, ObjectRegistry registry) {
        registry.registerObject("jdbcUtils", new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return JdbcUtils.class;
            }

            @Override
            public Dependency[] getInstanceDependencies() {
                return new Dependency[]{Dependency.type(DataSource.class)};
            }

            @Override
            public Object getInstance(Object[] params) {
                return new JdbcUtils((DataSource) params[0]);
            }
        });
    }
}
