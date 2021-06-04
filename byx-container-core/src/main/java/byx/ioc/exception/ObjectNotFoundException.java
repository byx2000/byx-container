package byx.ioc.exception;

/**
 * 找不到对象
 *
 * @author byx
 */
public class ObjectNotFoundException extends ByxContainerException {
    public ObjectNotFoundException(String id, Class<?> type) {
        super(String.format("Object not found: id=%s, type=%s",
                id, type));
    }
}
