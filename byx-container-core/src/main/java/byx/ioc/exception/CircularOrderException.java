package byx.ioc.exception;

import java.util.List;

/**
 * 顺序指定出现环路
 *
 * @author byx
 */
public class CircularOrderException extends ByxContainerException {
    public CircularOrderException(List<Class<?>> classes) {
        super("Circular order detected: " + classes);
    }
}
