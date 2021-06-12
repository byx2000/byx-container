package byx.ioc.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedElement;
import java.util.HashSet;
import java.util.LinkedList;
import java.util.Queue;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 注解工具类
 *
 * @author byx
 */
public class AnnotationUtils {
    /**
     * 获取所有复合注解
     *
     * @param element 目标元素
     * @return 注解实例集合
     */
    public static Set<Annotation> getAnnotations(AnnotatedElement element) {
        Set<AnnotatedElement> book = new HashSet<>();
        Queue<AnnotatedElement> queue = new LinkedList<>();
        queue.add(element);
        Set<Annotation> result = new HashSet<>();
        while (!queue.isEmpty()) {
            AnnotatedElement e = queue.remove();
            book.add(e);
            for (Annotation a : e.getAnnotations()) {
                result.add(a);
                if (!book.contains(a.annotationType())) {
                    queue.add(a.annotationType());
                }
            }
        }
        return result;
    }

    /**
     * 获取所有复合注解类型
     *
     * @param element 目标元素
     * @return 注解类型集合
     */
    public static Set<Class<? extends Annotation>> getAnnotationTypes(AnnotatedElement element) {
        return getAnnotations(element).stream().map(Annotation::annotationType).collect(Collectors.toSet());
    }

    /**
     * 是否标注指定注解
     *
     * @param element 目标元素
     * @param annotationType 注解类型
     * @return 是否标注指定注解
     */
    public static boolean hasAnnotation(AnnotatedElement element, Class<? extends Annotation> annotationType) {
        return getAnnotationTypes(element).contains(annotationType);
    }
}
