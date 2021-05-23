package byx.container.extension.orm.callback;

import byx.container.extension.orm.annotation.Dao;
import byx.ioc.core.AnnotationConfigContainerCallback;
import byx.ioc.core.Dependency;
import byx.ioc.core.ObjectDefinition;
import byx.ioc.core.PackageContext;
import byx.orm.DaoGenerator;
import byx.util.jdbc.JdbcUtils;

/**
 * 动态注册Dao接口的实现类
 *
 * @author byx
 */
public class ByxOrmDaoCreator implements AnnotationConfigContainerCallback {
    @Override
    public void afterAnnotationConfigContainerInit(PackageContext ctx) {
        ctx.getAnnotationScanner().getClassesAnnotatedBy(Dao.class).forEach(c -> {
            ctx.getRegistry().registerObject(c.getName(), new ObjectDefinition() {
                @Override
                public Class<?> getType() {
                    return c;
                }

                @Override
                public Dependency[] getInstanceDependencies() {
                    return new Dependency[]{Dependency.type(JdbcUtils.class)};
                }

                @Override
                public Object getInstance(Object[] params) {
                    return new DaoGenerator((JdbcUtils) params[0]).generate(c);
                }
            });
        });
    }
}
