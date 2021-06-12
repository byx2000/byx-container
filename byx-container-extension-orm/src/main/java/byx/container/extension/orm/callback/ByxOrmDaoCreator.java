package byx.container.extension.orm.callback;

import byx.container.extension.orm.annotation.Dao;
import byx.ioc.annotation.core.AnnotationConfigContainerCallback;
import byx.ioc.annotation.core.AnnotationConfigContainerContext;
import byx.ioc.annotation.core.ObjectDefinition;
import byx.ioc.util.ClassPredicates;
import byx.ioc.core.Dependency;
import byx.orm.core.DaoGenerator;

/**
 * 动态注册Dao接口的实现类
 *
 * @author byx
 */
public class ByxOrmDaoCreator implements AnnotationConfigContainerCallback {
    @Override
    public void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx) {
        ctx.getPackageScanner().getClasses(ClassPredicates.hasAnnotation(Dao.class))
                .forEach(c -> ctx.getObjectRegistry().registerObject(c.getName(), new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return c;
            }

            @Override
            public Dependency[] getDependencies() {
                return new Dependency[]{Dependency.type(DaoGenerator.class)};
            }

            @Override
            public Object getInstance(Object[] dependencies) {
                return ((DaoGenerator) dependencies[0]).generate(c);
            }
        }));
    }
}
