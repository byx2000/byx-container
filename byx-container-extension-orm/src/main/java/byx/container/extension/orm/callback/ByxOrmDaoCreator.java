package byx.container.extension.orm.callback;

import byx.ioc.core.Container;
import byx.ioc.core.ObjectCallback;
import byx.ioc.core.ObjectCallbackContext;

public class ByxOrmDaoCreator implements ObjectCallback {
    @Override
    public Object afterObjectWrap(ObjectCallbackContext ctx) {
        Object obj = ctx.getObject();
        Class<?> type = obj.getClass();
        Container container = ctx.getContainer();

        return null;
    }
}
