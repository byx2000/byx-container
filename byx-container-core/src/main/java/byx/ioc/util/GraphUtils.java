package byx.ioc.util;

import java.util.ArrayList;
import java.util.List;

/**
 * 图工具类
 *
 * @author byx
 */
public class GraphUtils {
    /**
     * 拓扑排序
     * @param adj 邻接矩阵
     * @return 排序后的顶点索引
     */
    public static List<Integer> topologicalSort(boolean[][] adj) {
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

        List<Integer> result = new ArrayList<>();

        // 不停地删除入度为0的节点，直到无法继续删除
        while (!ready.isEmpty()) {
            // 获取第一个入度为0的节点，从ready中移除
            int i = ready.remove(0);
            // 将节点添加到结果列表
            result.add(i);
            // 更新与之相连的节点的入度
            for (int j = 0; j < n; ++j) {
                if (adj[i][j]) {
                    in[j]--;
                    // 如果有新的节点入度变为0，则添加到ready中
                    if (in[j] == 0) {
                        ready.add(j);
                    }
                }
            }
        }

        return result;
    }
}
