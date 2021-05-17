package byx.ioc.extension.transaction;

import byx.ioc.core.Container;
import byx.ioc.core.ObjectCallback;
import byx.ioc.core.ObjectCallbackContext;
import byx.transaction.TransactionManager;
import byx.transaction.annotation.Transactional;
import byx.util.jdbc.JdbcUtils;

import java.util.Arrays;

/**
 * 创建事务增强代理对象
 *
 * @author byx
 */
public class TransactionalProxyCreator implements ObjectCallback {
    @Override
    public Object afterObjectWrap(ObjectCallbackContext ctx) {
        // 如果对象的某个方法标注了Transaction注解，则返回事务增强代理对象
        // 否则返回原对象
        Object obj = ctx.getObject();
        boolean flag = Arrays.stream(obj.getClass().getMethods())
                .anyMatch(m -> m.isAnnotationPresent(Transactional.class));
        if (flag) {
            Container container = ctx.getContainer();
            return TransactionManager.getProxy(container.getObject(JdbcUtils.class), obj);
        }
        return obj;
    }
}
