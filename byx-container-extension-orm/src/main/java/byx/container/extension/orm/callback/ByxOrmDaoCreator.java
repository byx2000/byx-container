package byx.container.extension.orm.callback;

import byx.container.extension.orm.annotation.Dao;
import byx.ioc.core.ObjectCallback;
import byx.ioc.core.ObjectCallbackContext;
import byx.orm.DaoGenerator;

/**
 * 创建Dao接口的实现类
 *
 * @author byx
 */
public class ByxOrmDaoCreator implements ObjectCallback {
    @Override
    public Object afterObjectWrap(ObjectCallbackContext ctx) {
        Class<?> type = ctx.getType();
        if (type.isInterface() && type.isAnnotationPresent(Dao.class)) {
            return ctx.getContainer().getObject(DaoGenerator.class).generate(type);
        }
        return ctx.getObject();
    }
}
