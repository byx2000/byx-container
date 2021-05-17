package byx.ioc.exception;

/**
 * 加载Jar包资源文件时出错
 *
 * @author byx
 */
public class LoadJarResourceException extends ByxContainerException {
    public LoadJarResourceException(Throwable cause) {
        super("Error occurred while loading jar resources.", cause);
    }
}
