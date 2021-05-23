package byx.ioc.core;

import byx.ioc.exception.*;
import byx.ioc.util.JarUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.*;
import java.util.function.Supplier;
import java.util.stream.Collectors;

/**
 * 抽象容器
 * 包含对扩展组件的加载和循环依赖的检测预处理
 *
 * 通过JDK自带的SPI机制来加载扩展组件
 * 扩展组件包括ObjectCallback、ContainerCallback和ValueConverter
 * ObjectCallback类似于Spring的BeanPostProcessor
 * ContainerCallback类似于Spring的ApplicationListener
 * ValueConverter用于转换不同数据类型的值
 *
 * 使用基于拓扑排序的循环依赖检测算法
 * 以及基于二级缓存的循环依赖处理算法
 *
 * @author byx
 */
public abstract class AbstractContainer implements Container {
    /**
     * 保存所有ObjectDefinition
     */
    private final Map<String, ObjectDefinition> definitions = new HashMap<>();

    /**
     * 一级缓存：存放已完全初始化的对象
     */
    private final Map<String, Object> cache1 = new HashMap<>();

    /**
     * 二级缓存：存放已实例化对象的工厂
     * 该工厂包含对已实例化对象的代理操作
     * 通过调用ObjectDefinition的doWrap方法
     */
    private final Map<String, Supplier<Object>> cache2 = new HashMap<>();

    /**
     * 保存所有对象回调器
     */
    private static final List<ObjectCallback> OBJECT_CALLBACKS;

    /**
     * 保存所有容器回调器
     */
    private static final List<ContainerCallback> CONTAINER_CALLBACKS;

    /**
     * 保存所有值转换器
     */
    private static final List<ValueConverter> VALUE_CONVERTERS;

    /**
     * 额外导入的组件
     */
    private final static String COMPONENTS_EXPORT_FILE_NAME = "META-INF/components/components.export";
    private final static List<Class<?>> EXPORT_COMPONENTS;

    static {
        // 加载扩展组件
        OBJECT_CALLBACKS = loadObjectCallbacks();
        OBJECT_CALLBACKS.sort(Comparator.comparingInt(ObjectCallback::getOrder));
        CONTAINER_CALLBACKS = loadContainerCallbacks();
        CONTAINER_CALLBACKS.sort(Comparator.comparingInt(ContainerCallback::getOrder));
        VALUE_CONVERTERS = loadValueConverters();

        // 加载使用SPI导入的类
        EXPORT_COMPONENTS = loadExportComponents();
    }

    /**
     * 加载所有ObjectCallback
     */
    private static List<ObjectCallback> loadObjectCallbacks() {
        List<ObjectCallback> ocs = new ArrayList<>();
        for (ObjectCallback oc : ServiceLoader.load(ObjectCallback.class)) {
            ocs.add(oc);
        }
        return ocs;
    }

    /**
     * 加载所有ContainerCallback
     */
    private static List<ContainerCallback> loadContainerCallbacks() {
        List<ContainerCallback> ccs = new ArrayList<>();
        for (ContainerCallback cc : ServiceLoader.load(ContainerCallback.class)) {
            ccs.add(cc);
        }
        return ccs;
    }

    /**
     * 加载所有ValueConverter
     */
    private static List<ValueConverter> loadValueConverters() {
        List<ValueConverter> vcs = new ArrayList<>();
        for (ValueConverter vc : ServiceLoader.load(ValueConverter.class)) {
            vcs.add(vc);
        }
        return vcs;
    }

    /**
     * 加载使用SPI导入的类
     */
    private static List<Class<?>> loadExportComponents() {
        List<Class<?>> components = new ArrayList<>();
        try {
            List<URL> urls = JarUtils.getJarResources(COMPONENTS_EXPORT_FILE_NAME);
            for (URL url : urls) {
                BufferedReader reader = new BufferedReader(new InputStreamReader(url.openStream()));
                reader.lines().forEach(line -> {
                    try {
                        components.add(Class.forName(line));
                    } catch (ClassNotFoundException e) {
                        throw new LoadJarResourceException(e);
                    }
                });
            }
        } catch (Exception e) {
            throw new LoadJarResourceException(e);
        }

        return components;
    }

    /**
     * 子类通过调用该方法来向容器中注册对象
     *
     * @param id id
     * @param definition 对象定义
     */
    protected void registerObject(String id, ObjectDefinition definition) {
        definitions.put(id, definition);
        checkCircularDependency();
    }

    /**
     * 子类初始化好容器后需要调用该方法，以调用扩展组件的回调方法
     */
    protected void afterContainerInit() {
        for (ContainerCallback cc : CONTAINER_CALLBACKS) {
            cc.afterContainerInit(this, definitions::put);
        }
    }

    /**
     * 子类通过调用该方法来获取指定的值转换器
     *
     * @param fromType 源类型
     * @param toType 目标类型
     * @return 返回指定的转换器，如果找不到则返回null
     */
    protected ValueConverter getValueConverter(Class<?> fromType, Class<?> toType) {
        for (ValueConverter vc : VALUE_CONVERTERS) {
            if (vc.fromType() == fromType && vc.toType() == toType) {
                return vc;
            }
        }
        return null;
    }

    /**
     * 子类通过调用该方法来获取额外导入的组件
     * @return 组件列表
     */
    protected List<Class<?>> getExportComponents() {
        return EXPORT_COMPONENTS;
    }

    /**
     * 获取特定类型的ContainerCallback
     * @param type 类型
     * @param <T> 类型
     * @return ContainerCallback列表
     */
    protected <T extends ContainerCallback> List<T> getContainerCallbacks(Class<T> type) {
        return CONTAINER_CALLBACKS.stream()
                .filter(c -> type.isAssignableFrom(c.getClass()))
                .map(type::cast)
                .sorted(Comparator.comparingInt(ContainerCallback::getOrder))
                .collect(Collectors.toList());
    }

    @Override
    @SuppressWarnings("unchecked")
    public  <T> T getObject(String id) {
        checkIdExist(id);
        return (T) createOrGetObject(id, definitions.get(id));
    }

    @Override
    public  <T> T getObject(Class<T> type) {
        List<String> candidates = definitions.keySet().stream()
                .filter(id -> type.isAssignableFrom(definitions.get(id).getType()))
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
            throw new TypeNotFoundException(type);
        }
        return type.cast(obj);
    }

    @Override
    @SuppressWarnings("unchecked")
    public <T> Set<T> getObjects(Class<T> type) {
        Set<Object> objects = definitions.keySet().stream()
                .filter(id -> type.isAssignableFrom(definitions.get(id).getType()))
                .map(id -> createOrGetObject(id, definitions.get(id)))
                .collect(Collectors.toSet());
        return (Set<T>) objects;
    }

    @Override
    public Set<String> getObjectIds() {
        return new HashSet<>(definitions.keySet());
    }

    @Override
    public Set<Class<?>> getObjectTypes() {
        return definitions.values().stream()
                .map(ObjectDefinition::getType)
                .collect(Collectors.toSet());
    }

    /**
     * 创建依赖项
     */
    private Object createDependency(Dependency dependency) {
        if (dependency.getId() != null) {
            return getObject(dependency.getId());
        } else if (dependency.getType() != null) {
            return getObject(dependency.getType());
        }
        throw new BadDependencyException(dependency);
    }

    /**
     * 创建依赖数组
     */
    private Object[] createDependencies(Dependency[] dependencies) {
        Object[] params = new Object[dependencies.length];
        for (int i = 0; i < dependencies.length; ++i) {
            params[i] = createDependency(dependencies[i]);
        }
        return params;
    }

    /**
     * 获取类型对应的id
     */
    private String getTypeId(Class<?> type) {
        List<String> candidates = definitions.keySet().stream()
                .filter(id -> type.isAssignableFrom(definitions.get(id).getType()))
                .collect(Collectors.toList());

        if (candidates.size() != 1) {
            return null;
        }

        return candidates.get(0);
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
    private Object createOrGetObject(String id, ObjectDefinition definition) {
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

        // 获取并创建对象实例化的依赖项
        Object[] params = createDependencies(definition.getInstanceDependencies());

        // 查找一级缓存和二级缓存，如果找到则直接返回
        if (cache1.containsKey(id)) {
            return cache1.get(id);
        }
        if (cache2.containsKey(id)) {
            Object obj = cache2.get(id).get();
            cache2.remove(id);
            cache1.put(id, obj);
            return obj;
        }

        // 实例化对象
        Object obj = definition.getInstance(params);

        // 将实例化后的对象加入二级缓存
        cache2.put(id, () -> {
            Object o = definition.doWrap(obj);

            // 回调afterObjectWrap
            for (ObjectCallback oc : OBJECT_CALLBACKS) {
                o = oc.afterObjectWrap(new ObjectCallbackContext(o, definition.getType(), this, definition, id));
            }

            return o;
        });

        // 初始化对象
        definition.doInit(obj);

        // 回调afterObjectInit
        for (ObjectCallback oc : OBJECT_CALLBACKS) {
            oc.afterObjectInit(new ObjectCallbackContext(obj, definition.getType(), this, definition, id));
        }

        return createOrGetObject(id, definition);
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
            Dependency[] dependencies = definitions.get(id).getInstanceDependencies();
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

        // in存储所有节点的入度
        // all集合存储当前还未排序的节点编号
        // ready集合存储当前入度为0的节点
        int[] in = new int[n];
        Set<Integer> all = new HashSet<>();
        List<Integer> ready = new ArrayList<>();
        for (int i = 0; i < n; ++i) {
            all.add(i);
            in[i] = 0;
        }
        for (int i = 0; i < n; ++i) {
            for (int j = 0; j < n; ++j) {
                if (adj[i][j]) {
                    in[j]++;
                }
            }
        }
        for (int i = 0; i < n; ++i) {
            if (in[i] == 0) {
                ready.add(i);
            }
        }

        // 按照拓扑排序的顺序删除节点
        while (!ready.isEmpty()) {
            int cur = ready.remove(0);
            all.remove(cur);
            for (int i = 0; i < n; ++i) {
                if (adj[cur][i]) {
                    in[i]--;
                    if (in[i] == 0) {
                        ready.add(i);
                    }
                }
            }
        }

        // 如果还有未排序的节点，说明依赖图中存在环路，即发生了循环依赖
        if (!all.isEmpty()) {
            // 获取构成循环依赖的对象id
            List<String> circularIds = new ArrayList<>();
            for (int i : all) {
                circularIds.add(ids.get(i));
            }
            throw new CircularDependencyException(circularIds);
        }
    }

    /**
     * 检查id是否存在
     */
    private void checkIdExist(String id) {
        if (!definitions.containsKey(id)) {
            throw new IdNotFoundException(id);
        }
    }
}
