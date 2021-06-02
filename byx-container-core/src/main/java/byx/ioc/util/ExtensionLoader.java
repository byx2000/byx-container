package byx.ioc.util;

import byx.ioc.exception.LoadJarResourceException;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
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
     * 获取指定类型的所有扩展类
     *
     * @param type 类型
     * @return 类型列表
     */
    public static List<Class<?>> getExtensionClassesOfType(Class<?> type) {
        return EXTENSION_CLASSES.stream()
                .filter(type::isAssignableFrom)
                .collect(Collectors.toList());
    }

    /**
     * 获取标注了指定注解的所有扩展类
     *
     * @param annotationType 注解类型
     * @return 类型列表
     */
    public static List<Class<?>> getExtensionClassesWithAnnotation(Class<? extends Annotation> annotationType) {
        return EXTENSION_CLASSES.stream()
                .filter(c -> c.isAnnotationPresent(annotationType))
                .collect(Collectors.toList());
    }

    /**
     * 获取指定类型的所有扩展并创建对象
     *
     * @param type 类型
     * @param <T> 类型
     * @return 对象列表
     */
    public static <T> List<T> getExtensionObjectsOfType(Class<T> type) {
        return EXTENSION_CLASSES.stream()
                .filter(type::isAssignableFrom)
                .map(c -> type.cast(createByDefaultConstructor(c)))
                .collect(Collectors.toList());
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
