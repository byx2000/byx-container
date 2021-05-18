package byx.ioc.extension.aop.callback;

import byx.ioc.core.Container;
import byx.ioc.core.ObjectCallback;
import byx.ioc.core.ObjectCallbackContext;
import byx.ioc.extension.aop.annotation.ImplementedBy;
import byx.util.proxy.ProxyUtils;
import byx.util.proxy.core.MethodInterceptor;

/**
 * 处理ImplementedBy注解
 *
 * @author byx
 */
public class ImplementedByProcessor implements ObjectCallback {
    @Override
    public Object afterObjectWrap(ObjectCallbackContext ctx) {
        Class<?> type = ctx.getType();
        if (type.isInterface() && type.isAnnotationPresent(ImplementedBy.class)) {
            Container container = ctx.getContainer();
            Class<?> implType =  type.getAnnotation(ImplementedBy.class).value();
            Object impl = container.getObject(implType);
            return ProxyUtils.implement(type, MethodInterceptor.delegateTo(impl));
        }
        return ctx.getObject();
    }
}
