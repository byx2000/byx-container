package byx.ioc.annotation.util;

import java.lang.annotation.Annotation;
import java.lang.reflect.AnnotatedType;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

public class ClassPredicates {
    public static Predicate<Class<?>> hasAnnotation(Class<? extends Annotation> annotationType) {
        return c -> c.isAnnotationPresent(annotationType);
    }

    public static Predicate<Class<?>> isAssignableTo(Class<?> type) {
        return type::isAssignableFrom;
    }

    /*public static Predicate<?> hasMergedAnnotation(Class<? extends Annotation> annotationType) {
        return c -> getTypeAnnotations(c).
    }*/

    /*private static Map<Class<? extends Annotation>, Annotation> getTypeAnnotations(AnnotatedType type) {
        Annotation[] annotations = type.getAnnotations();
        Queue<Annotation> queue = new LinkedList<>(Arrays.asList(annotations));
        Set<Annotation> book = new HashSet<>(Set.of(annotations));
        Set<Annotation> result = new HashSet<>();
        while (!queue.isEmpty()) {
            Annotation a = queue.remove();
            result.add(a);
            for (Annotation aa : a.annotationType().getAnnotations()) {
                if (!book.contains(aa)) {
                    queue.add(aa);
                    book.add(aa);
                }
            }
        }
        return result.stream()
                .collect(Collectors.toMap(Annotation::annotationType, a -> a, (e, r) -> e));
    }*/


}
