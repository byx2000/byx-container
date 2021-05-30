package byx.ioc.util;

import java.util.ArrayList;
import java.util.Comparator;
import java.util.List;
import java.util.function.Function;

/**
 * 图算法工具类
 *
 * @author byx
 */
public class GraphUtils {
    /**
     * 拓扑排序
     * @param adj 邻接矩阵
     * @param comparator 对当前所有ready节点进行排序
     * @param toElement 将节点编号转换为具体的元素
     * @param <T> 元素类型
     * @return 排序后的节点编号序列
     */
    public static <T> List<T> topologicalSort(boolean[][] adj, Comparator<Integer> comparator, Function<Integer, T> toElement) {
        int n = adj.length;

        // 计算所有节点的入度
        int[] in = new int[n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (adj[i][j]) {
                    in[j]++;
                }
            }
        }

        // 获取所有入度为0的节点
        List<Integer> ready = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            if (in[i] == 0) {
                ready.add(i);
            }
        }

        List<T> result = new ArrayList<>();
        while (!ready.isEmpty()) {
            // 使用传入的comparator进行排序
            ready.sort(comparator);

            List<Integer> newReady = new ArrayList<>();
            for (int i : ready) {
                result.add(toElement.apply(i));
                for (int j = 0; j < n; ++j) {
                    if (adj[i][j]) {
                        in[j]--;
                        // 如果有新的节点入度变为0，则添加到ready中
                        if (in[j] == 0) {
                            newReady.add(j);
                        }
                    }
                }
            }
            ready = newReady;
        }

        return result;
    }

    /**
     * 拓扑排序，返回排序后的节点数组
     * @param adj 邻接矩阵
     * @return 排序后的节点数组
     */
    public static List<Integer> topologicalSort(boolean[][] adj) {
        return topologicalSort(adj, Comparator.comparingInt(a -> a), i -> i);
    }
}
