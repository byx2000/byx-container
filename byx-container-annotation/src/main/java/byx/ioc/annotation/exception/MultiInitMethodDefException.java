package byx.ioc.annotation.exception;

import byx.ioc.exception.ByxContainerException;

/**
 * 找到多个init函数
 *
 * @author byx
 */
public class MultiInitMethodDefException extends ByxContainerException {
    public MultiInitMethodDefException(Class<?> type) {
        super("Multi init method definitions in " + type);
    }
}
