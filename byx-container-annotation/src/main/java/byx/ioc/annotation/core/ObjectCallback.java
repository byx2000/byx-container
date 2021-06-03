package byx.ioc.annotation.core;

/**
 * 对象回调器
 *
 * @author byx
 */
public interface ObjectCallback {
    /**
     * 对象初始化后回调
     *
     * @param ctx 上下文
     */
    default void afterObjectInit(ObjectContext ctx) {

    }

    /**
     * 对象包装后回调
     *
     * @param ctx 上下文
     * @return 包装后的对象
     */
    default Object afterObjectWrap(ObjectContext ctx) {
        return ctx.getObject();
    }
}
