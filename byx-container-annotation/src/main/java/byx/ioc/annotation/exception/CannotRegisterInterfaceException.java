package byx.ioc.annotation.exception;

import byx.ioc.exception.ByxContainerException;

/**
 * 无法注册接口类型
 *
 * @author byx
 */
public class CannotRegisterInterfaceException extends ByxContainerException {
    public CannotRegisterInterfaceException(Class<?> interfaceType) {
        super("Cannot register interface type: " + interfaceType);
    }
}
