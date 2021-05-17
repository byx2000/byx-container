package byx.ioc.extension.aop.callback;

import byx.aop.ByxAOP;
import byx.ioc.core.Container;
import byx.ioc.core.ObjectCallback;
import byx.ioc.core.ObjectCallbackContext;
import byx.ioc.extension.aop.annotation.AdviceBy;

/**
 * ByxAOP处理器
 *
 * @author byx
 */
public class ByxAopProcessor implements ObjectCallback {
    @Override
    public Object afterObjectWrap(ObjectCallbackContext ctx) {
        Object obj = ctx.getObject();
        Container container = ctx.getContainer();

        // 如果标注了AdviceBy注解，则创建并返回AOP代理对象
        if (obj.getClass().isAnnotationPresent(AdviceBy.class)) {
            Class<?> adviceClass = obj.getClass().getAnnotation(AdviceBy.class).value();
            return ByxAOP.getAopProxy(obj, container.getObject(adviceClass));
        }

        // 否则直接返回原对象
        return obj;
    }
}
