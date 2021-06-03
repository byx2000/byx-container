package byx.ioc.extension.aop.callback;

import byx.ioc.annotation.core.AnnotationConfigContainerCallback;
import byx.ioc.annotation.core.AnnotationConfigContainerContext;
import byx.ioc.core.ObjectDefinition;
import byx.ioc.extension.aop.annotation.ImplementedBy;
import byx.util.proxy.ProxyUtils;
import byx.util.proxy.core.MethodInterceptor;

/**
 * 处理ImplementedBy注解
 *
 * @author byx
 */
public class ImplementedByProcessor implements AnnotationConfigContainerCallback {
    @Override
    public void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx) {
        ctx.getAnnotationScanner().getClassesAnnotatedBy(ImplementedBy.class)
                .forEach(c -> {
                    Class<?> implType = c.getAnnotation(ImplementedBy.class).value();
                    ctx.getObjectRegistry().registerObject(c.getName(), new ObjectDefinition() {
                        @Override
                        public Class<?> getType() {
                            return c;
                        }

                        @Override
                        public Object getInstance(Object[] dependencies) {
                            Object impl = ctx.getContainer().getObject(implType);
                            return ProxyUtils.implement(c, MethodInterceptor.delegateTo(impl));
                        }
                    });
                });
    }
}
