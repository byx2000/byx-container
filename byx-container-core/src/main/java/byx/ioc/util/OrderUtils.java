package byx.ioc.util;

import byx.ioc.annotation.Order;
import byx.ioc.exception.CircularOrderException;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;

/**
 * 类型排序工具类
 *
 * @author byx
 */
public class OrderUtils {
    /**
     * 根据Order注解对类型进行排序
     * @param classes 类型列表
     * @return 排序后的类型列表
     */
    public static List<Class<?>> sort(List<Class<?>> classes) {
        boolean[][] adj = getAdjacencyMatrix(classes);
        List<Class<?>> result = GraphUtils.topologicalSort(adj,
                Comparator.comparingInt(i -> getOrderValue(classes.get(i))),
                classes::get);
        if (result.size() != classes.size()) {
            List<Class<?>> circular = new ArrayList<>();
            for (Class<?> c : classes) {
                if (!result.contains(c)) {
                    circular.add(c);
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
