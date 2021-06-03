package byx.container.extension.orm.callback;

import byx.container.extension.orm.annotation.Dao;
import byx.ioc.annotation.core.AnnotationConfigContainerCallback;
import byx.ioc.annotation.core.AnnotationConfigContainerContext;
import byx.ioc.core.Dependency;
import byx.ioc.core.ObjectDefinition;
import byx.orm.core.DaoGenerator;
import byx.util.jdbc.JdbcUtils;

/**
 * 动态注册Dao接口的实现类
 *
 * @author byx
 */
public class ByxOrmDaoCreator implements AnnotationConfigContainerCallback {
    @Override
    public void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx) {
        ctx.getAnnotationScanner().getClassesAnnotatedBy(Dao.class).forEach(c -> {
            ctx.getObjectRegistry().registerObject(c.getName(), new ObjectDefinition() {
                @Override
                public Class<?> getType() {
                    return c;
                }

                @Override
                public Dependency[] getDependencies() {
                    return new Dependency[]{Dependency.type(JdbcUtils.class)};
                }

                @Override
                public Object getInstance(Object[] dependencies) {
                    return new DaoGenerator((JdbcUtils) dependencies[0]).generate(c);
                }
            });
        });
    }
}
