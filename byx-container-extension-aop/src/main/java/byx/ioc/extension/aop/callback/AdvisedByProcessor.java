package byx.ioc.extension.aop.callback;

import byx.aop.ByxAOP;
import byx.ioc.annotation.Order;
import byx.ioc.annotation.callback.InitProcessor;
import byx.ioc.core.Container;
import byx.ioc.annotation.core.ObjectCallback;
import byx.ioc.annotation.core.ObjectContext;
import byx.ioc.extension.aop.annotation.AdvisedBy;

/**
 * 处理AdviceBy注解
 *
 * @author byx
 */
@Order(before = InitProcessor.class)
public class AdvisedByProcessor implements ObjectCallback {
    @Override
    public Object afterObjectWrap(ObjectContext ctx) {
        Object obj = ctx.getObject();
        if (obj == null) {
            return null;
        }

        Container container = ctx.getContainer();

        // 如果标注了AdviceBy注解，则创建并返回AOP代理对象
        if (obj.getClass().isAnnotationPresent(AdvisedBy.class)) {
            Class<?> adviceClass = obj.getClass().getAnnotation(AdvisedBy.class).value();
            return ByxAOP.getAopProxy(obj, container.getObject(adviceClass));
        }

        // 否则直接返回原对象
        return obj;
    }
}
