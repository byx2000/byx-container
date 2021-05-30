package byx.ioc.util;

import byx.ioc.annotation.Order;
import byx.ioc.exception.CircularOrderException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组件排序工具类
 *
 * @author byx
 */
public class OrderUtils {
    /**
     * 根据Order注解对组件进行排序
     * @param objects 组件列表
     * @param <T> 类型
     * @return 排序后的组件列表
     */
    public static <T> List<T> sort(List<T> objects) {
        boolean[][] adj = getAdjacencyMatrix(objects.stream()
                .map(Object::getClass)
                .collect(Collectors.toList()));
        List<T> result = GraphUtils.topologicalSort(adj,
                Comparator.comparingInt(i -> getOrderValue(objects.get(i).getClass())),
                objects::get);
        // 检测到环路
        if (result.size() != objects.size()) {
            List<Class<?>> circular = new ArrayList<>();
            for (T e : objects) {
                if (!result.contains(e)) {
                    circular.add(e.getClass());
                }
            }
            throw new CircularOrderException(circular);
        }
        return result;
    }

    private static boolean[][] getAdjacencyMatrix(List<Class<?>> classes) {
        int n = classes.size();
        boolean[][] adj = new boolean[n][n];

        for (int i = 0; i < n; ++i) {
            Class<?> c = classes.get(i);
            Class<?>[] before = getBeforeClasses(c);
            Class<?>[] after = getAfterClasses(c);
            for (Class<?> cc : before) {
                for (int j = 0; j < n; ++j) {
                    if (classes.get(j) == cc) {
                        adj[j][i] = true;
                    }
                }
            }
            for (Class<?> cc : after) {
                for (int j = 0; j < n; ++j) {
                    if (classes.get(j) == cc) {
                        adj[i][j] = true;
                    }
                }
            }
        }

        return adj;
    }

    private static int getOrderValue(Class<?> c) {
        if (c.isAnnotationPresent(Order.class)) {
            return c.getAnnotation(Order.class).value();
        }
        return 1;
    }

    private static Class<?>[] getBeforeClasses(Class<?> c) {
        if (c.isAnnotationPresent(Order.class)) {
            return c.getAnnotation(Order.class).before();
        }
        return new Class[]{};
    }

    private static Class<?>[] getAfterClasses(Class<?> c) {
        if (c.isAnnotationPresent(Order.class)) {
            return c.getAnnotation(Order.class).after();
        }
        return new Class[]{};
    }
}
