package byx.ioc.util;

import byx.ioc.exception.LoadJarResourceException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
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
     * 获取所有扩展类
     *
     * @return 扩展类列表
     */
    public static List<Class<?>> getExtensions() {
        return EXTENSION_CLASSES;
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

    /**
     * 获取指定类型的所有扩展类
     *
     * @param type 类型
     * @return 类型列表
     */
    public static List<Class<?>> getExtensionsOfType(Class<?> type) {
        return getExtensions(type::isAssignableFrom);
    }

    /**
     * 获取标注了指定注解的所有扩展类
     *
     * @param annotationType 注解类型
     * @return 类型列表
     */
    public static List<Class<?>> getExtensionsWithAnnotation(Class<? extends Annotation> annotationType) {
        return getExtensions(c -> c.isAnnotationPresent(annotationType));
    }

    /**
     * 获取指定类型的所有扩展并创建对象
     *
     * @param type 类型
     * @param <T> 类型
     * @return 对象列表
     */
    public static <T> List<T> getExtensionInstancesOfType(Class<T> type) {
        return getExtensions(type::isAssignableFrom, c -> type.cast(createByDefaultConstructor(c)));
    }

    /**
     * 使用默认构造函数创建对象
     */
    private static Object createByDefaultConstructor(Class<?> type) {
        try {
            Constructor<?> constructor = type.getConstructor();
            constructor.setAccessible(true);
            return constructor.newInstance();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
