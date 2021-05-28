package byx.ioc.util;

import byx.ioc.annotation.Order;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 组件排序工具类
 *
 * @author byx
 */
public class OrderUtils {
    /**
     * 根据Order注解对多个对象进行排序
     * @param orders 对象列表
     * @param <T> 对象类型
     * @return 排序后的对象列表
     */
    public static <T> List<T> sort(List<T> orders) {
        int n = orders.size();

        // 构造邻接矩阵
        boolean[][] adj = new boolean[n][n];
        List<Class<?>> classes = orders.stream().map(Object::getClass).collect(Collectors.toList());
        for (int i = 0; i < n; ++i) {
            Object o = orders.get(i);
            Class<?>[] before = new Class[0];
            Class<?>[] after = new Class[0];
            if (o.getClass().isAnnotationPresent(Order.class)) {
                before = o.getClass().getAnnotation(Order.class).before();
                after = o.getClass().getAnnotation(Order.class).after();
            }
            for (Class<?> c : before) {
                for (int j = 0; j < n; ++j) {
                    if (classes.get(j) == c) {
                        adj[j][i] = true;
                    }
                }
            }
            for (Class<?> c : after) {
                for (int j = 0; j < n; ++j) {
                    if (classes.get(j) == c) {
                        adj[i][j] = true;
                    }
                }
            }
        }

        List<Integer> ids = GraphUtils.topologicalSort(adj);
        if (ids.size() != n) {

            throw new RuntimeException("循环依赖");
        }

        List<T> result = new ArrayList<>();
        for (int i : ids) {
            result.add(orders.get(i));
        }

        return result;
    }
}
