package byx.ioc.core;

import byx.ioc.exception.*;
import byx.ioc.util.GraphUtils;

import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 支持循环依赖解析的容器
 * 该容器使用二级缓存机制进行循环依赖的处理
 * 并使用拓扑排序实现循环依赖的检测
 * 只支持单例组件
 *
 * 该类实现了Container中的全部方法，同时开放了若干抽象方法供子类实现
 *
 * @param <D> 对象定义的类型
 * @author byx
 */
public abstract class CachedContainer<D> implements Container, ObjectRegistry<D> {
    /**
     * 从definition获取对象类型
     *
     * @param definition 对象定义
     * @return 对象类型
     */
    protected abstract Class<?> getType(D definition);

    /**
     * 从definition获取对象依赖项
     *
     * @param definition 对象定义
     * @return 依赖项数组
     */
    protected abstract Dependency[] getDependencies(D definition);

    /**
     * 根据definition实例化对象
     *
     * @param definition 对象定义
     * @param id 对象id
     * @param dependencies 依赖项
     * @return 实例化的对象
     */
    protected abstract Object doInstantiate(D definition, String id, Object[] dependencies);

    /**
     * 根据definition初始化对象
     *  @param definition 对象定义
     * @param id 对象id
     * @param obj 实例化后的对象
     */
    protected abstract void doInit(D definition, String id, Object obj);

    /**
     * 根据definition包装对象
     *
     * @param definition 对象定义
     * @param id 对象id
     * @param obj 初始化后的对象
     * @return 包装后的对象
     */
    protected abstract Object doWrap(D definition, String id, Object obj);

    /**
     * 保存所有(id, 对象定义)键值对
     */
    private final Map<String, D> definitions = new HashMap<>();

    /**
     * 一级缓存：存放已完全初始化的对象
     */
    private final Map<String, Object> cache1 = new HashMap<>();

    /**
     * 二级缓存：存放已实例化对象的工厂方法
     * 该工厂方法包含对已实例化对象的包装操作
     */
    private final Map<String, Supplier<Object>> cache2 = new HashMap<>();

    /**
     * 注册对象定义
     *
     * @param id id
     * @param definition 对象定义
     */
    @Override
    public void registerObject(String id, D definition) {
        definitions.put(id, definition);
        checkCircularDependency();
    }


    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject(String id) {
        checkIdExist(id);
        return (T) createOrGetObject(id, definitions.get(id));
    }

    @Override
    public <T> T getObject(Class<T> type) {
        List<String> candidates = definitions.keySet().stream()
                .filter(id -> type.isAssignableFrom(getType(definitions.get(id))))
                .collect(Collectors.toList());

        if (candidates.size() == 0) {
            throw new TypeNotFoundException(type);
        } else if (candidates.size() > 1) {
            throw new MultiTypeMatchException(type);
        }

        return getObject(candidates.get(0));
    }

    @Override
    public <T> T getObject(String id, Class<T> type) {
        Object obj = getObject(id);
        if (!type.isAssignableFrom(obj.getClass())) {
            throw new ObjectNotFoundException(id, type);
        }
        return type.cast(obj);
    }

    @Override
    public <T> Set<T> getObjects(Class<T> type) {
        return definitions.keySet().stream()
                .filter(id -> type.isAssignableFrom(getType(definitions.get(id))))
                .map(id -> type.cast(createOrGetObject(id, definitions.get(id))))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getObjectIds() {
        return new HashSet<>(definitions.keySet());
    }

    @Override
    public Set<Class<?>> getObjectTypes() {
        return definitions.values().stream()
                .map(this::getType)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<?> getType(String id) {
        if (definitions.containsKey(id)) {
            return getType(definitions.get(id));
        }
        throw new IdNotFoundException(id);
    }

    @Override
    public boolean exist(String id) {
        return definitions.containsKey(id);
    }

    @Override
    public long count(Class<?> type) {
        return definitions.values().stream()
                .filter(d -> type.isAssignableFrom(getType(d)))
                .count();
    }

    private void checkIdExist(String id) {
        if (!definitions.containsKey(id)) {
            throw new IdNotFoundException(id);
        }
    }

    private String getTypeId(Class<?> type) {
        List<String> candidates = definitions.keySet().stream()
                .filter(id -> type.isAssignableFrom(getType(definitions.get(id))))
                .collect(Collectors.toList());

        if (candidates.size() != 1) {
            return null;
        }

        return candidates.get(0);
    }

    private Object createDependency(Dependency dependency) {
        if (dependency.getId() != null) {
            return getObject(dependency.getId());
        } else if (dependency.getType() != null) {
            return getObject(dependency.getType());
        }
        throw new BadDependencyException(dependency);
    }

    private Object[] createDependencies(Dependency[] dependencies) {
        Object[] params = new Object[dependencies.length];
        for (int i = 0; i < dependencies.length; ++i) {
            params[i] = createDependency(dependencies[i]);
        }
        return params;
    }

    /**
     * 创建/获取容器中的对象
     * 循环依赖处理的核心算法
     *
     * 对象创建步骤：
     * 1. 查找一级缓存，如果找到则直接返回
     * 2. 查找二级缓存，如果找到，则取出对象工厂，执行代理操作，然后把代理后的对象移入一级缓存，
     *    并返回代理后的对象
     * 3. 如果两级缓存都没找到，说明对象是第一次创建，依次执行下面的步骤：
     *      1) 调用ObjectDefinition的getInstanceDependencies方法，获取对象实例化所需的依赖项
     *      2) 递归调用Container的getObject方法创建依赖项
     *      3) 调用ObjectDefinition的getInstance方法创建对象实例
     *      4) 使用对象工厂包装刚刚创建的对象实例，工厂内部调用ObjectDefinition的doWrap方法创建代理
     *      5) 把对象工厂放入二级缓存
     *      6) 调用ObjectDefinition的doInit方法初始化对象（属性填充）
     *      7) 再次尝试从缓存中获取对象，这次一定能够获取到
     */
    private Object createOrGetObject(String id, D definition) {
        Object obj;

        if ((obj = searchInCache(id)) != null) {
            return obj;
        }

        // 获取并创建对象实例化的依赖项
        Object[] params = createDependencies(getDependencies(definition));

        if ((obj = searchInCache(id)) != null) {
            return obj;
        }

        // 实例化对象
        obj = doInstantiate(definition, id, params);

        // 将实例化后的对象加入二级缓存
        Object finalObj = obj;
        cache2.put(id, () -> doWrap(definition, id, finalObj));

        // 初始化对象
        doInit(definition, id, obj);

        return createOrGetObject(id, definition);
    }

    private Object searchInCache(String id) {
        // 查找一级缓存，如果找到则直接返回
        if (cache1.containsKey(id)) {
            return cache1.get(id);
        }

        // 查找二级缓存，如果找到则调用get方法，然后把二级缓存移动到一级缓存
        if (cache2.containsKey(id)) {
            Object obj = cache2.get(id).get();
            cache2.remove(id);
            cache1.put(id, obj);
            return obj;
        }

        // 找不到
        return null;
    }

    /**
     * 循环依赖检测
     *
     * 步骤：
     * 1. 调用容器中所有ObjectDefinition的getInstanceDependencies，获取所有对象的实例化依赖项，
     *    将依赖关系转换成一张有向图
     * 2. 使用拓扑排序的顺序依次删除图中的节点，直到不能再删为止
     * 3. 如果还存在未删除的节点，说明依赖图中存在环路，即产生了循环依赖
     */
    private void checkCircularDependency() {
        // 初始化邻接表矩阵
        int n = definitions.size();
        boolean[][] adj = new boolean[n][n];
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                adj[i][j] = false;
            }
        }

        // 获取容器中所有对象id
        List<String> ids = new ArrayList<>(definitions.keySet());

        // 构建对象的构造函数依赖图
        for (int i = 0; i < n; ++i) {
            String id = ids.get(i);
            Dependency[] dependencies = getDependencies(definitions.get(id));
            for (Dependency dep : dependencies) {
                if (dep.getId() != null) {
                    int j = ids.indexOf(dep.getId());
                    if (j < 0) {
                        // 找不到依赖则直接忽略
                        continue;
                    }
                    adj[i][j] = true;
                } else if (dep.getType() != null) {
                    int j = ids.indexOf(getTypeId(dep.getType()));
                    if (j < 0) {
                        // 找不到依赖则直接忽略
                        continue;
                    }
                    adj[i][j] = true;
                } else {
                    throw new BadDependencyException(dep);
                }
            }
        }

        // 拓扑排序
        List<Integer> ordered = GraphUtils.topologicalSort(adj);

        // 检测到循环依赖，获取环路上的所有id
        if (ordered.size() != n) {
            List<String> circularIds = new ArrayList<>();
            for (int i = 0; i < n; ++i) {
                if (!ordered.contains(i)) {
                    circularIds.add(ids.get(i));
                }
            }
            throw new CircularDependencyException(circularIds);
        }
    }
}
