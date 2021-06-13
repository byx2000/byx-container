package byx.ioc.util;

import byx.ioc.exception.LoadJarResourceException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 扩展加载器
 *
 * @author byx
 */
public class ExtensionLoader {
    private static final String EXTENSION_FILE = "META-INF/byx-container.ext";
    private static List<Class<?>> EXTENSION_CLASSES = new ArrayList<>();

    /**
     * 通过默认构造函数创建对象实例
     */
    @SuppressWarnings("unchecked")
    public static <T> Function<Class<?>, T> createByDefaultConstructor() {
        return c -> {
            try {
                Constructor<?> constructor = c.getConstructor();
                constructor.setAccessible(true);
                return (T) constructor.newInstance();
            } catch (Exception e) {
                throw new RuntimeException(e);
            }
        };
    }

    static {
        // 从byx-container.ext文件加载所有扩展类
        try {
            List<URL> urls = JarUtils.getJarResources(EXTENSION_FILE);
            for (URL url : urls) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                reader.lines().forEach(line -> {
                    if (!line.trim().startsWith("#") && !line.isBlank()) {
                        try {
                            EXTENSION_CLASSES.add(Class.forName(line));
                        } catch (ClassNotFoundException e) {
                            throw new LoadJarResourceException(e);
                        }
                    }
                });
            }

            // 对扩展类型进行排序
            EXTENSION_CLASSES = OrderUtils.sort(EXTENSION_CLASSES);
        } catch (Exception e) {
            throw new LoadJarResourceException(e);
        }
    }

    /**
     * 获取所有符合条件的扩展类，并执行自定义转换操作
     *
     * @param predicate 条件
     * @param mapper 转换操作
     * @param <T> 转换后的类型
     * @return 结果列表
     */
    public static <T> List<T> getExtensions(Predicate<Class<?>> predicate, Function<Class<?>, T> mapper) {
        return EXTENSION_CLASSES.stream()
                .filter(predicate)
                .map(mapper)
                .collect(Collectors.toList());
    }

    /**
     * 获取所有符合条件的扩展类
     *
     * @param predicate 条件
     * @return 扩展类列表
     */
    public static List<Class<?>> getExtensions(Predicate<Class<?>> predicate) {
        return getExtensions(predicate, c -> c);
    }
}
