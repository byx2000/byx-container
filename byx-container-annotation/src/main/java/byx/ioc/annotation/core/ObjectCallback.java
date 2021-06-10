package byx.ioc.annotation.core;

/**
 * 对象回调器
 *
 * @author byx
 */
public interface ObjectCallback {
    /**
     * 对象实例化后回调
     *
     * @param ctx 上下文
     */
    default void afterObjectInstantiate(ObjectContext ctx) {

    }

    /**
     * 对象初始化后回调
     *
     * @param ctx 上下文
     */
    default void afterObjectInit(ObjectContext ctx) {

    }

    /**
     * 替换对象
     *
     * @param ctx 上下文
     * @return 替换后的对象
     */
    default Object replaceObject(ObjectContext ctx) {
        return ctx.getObject();
    }
}
