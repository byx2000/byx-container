package byx.ioc.core;

import byx.ioc.exception.IdNotFoundException;
import byx.ioc.exception.MultiTypeMatchException;
import byx.ioc.exception.ObjectNotFoundException;
import byx.ioc.exception.TypeNotFoundException;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * 简单容器
 * 使用Map管理所有对象定义
 * 无法处理循环依赖
 *
 * 该类实现了Container中的全部方法，同时开放了若干抽象方法供子类实现
 *
 * @param <D> 对象定义类型
 * @author byx
 */
public abstract class SimpleContainer<D> implements Container, ObjectRegistry<D> {
    /**
     * 根据对象定义创建对象
     *
     * @param definition 对象定义
     * @return 对象实例
     */
    protected abstract Object doCreate(D definition);

    /**
     * 根据对象定义获取对象类型
     *
     * @param definition 对象定义
     * @return 对象类型
     */
    protected abstract Class<?> doGetType(D definition);

    private final Map<String, D> definitions = new HashMap<>();

    @Override
    @SuppressWarnings("unchecked")
    public <T> T getObject(String id) {
        if (definitions.containsKey(id)) {
            return (T) doCreate(definitions.get(id));
        }
        throw new IdNotFoundException(id);
    }

    @Override
    public <T> T getObject(Class<T> type) {
        List<D> candidates = getAllDefinitionsOfType(type);
        if (candidates.size() == 0) {
            throw new TypeNotFoundException(type);
        } else if (candidates.size() > 1) {
            throw new MultiTypeMatchException(type);
        }
        return type.cast(doCreate(candidates.get(0)));
    }

    @Override
    public <T> T getObject(String id, Class<T> type) {
        if (definitions.containsKey(id)) {
            D d = definitions.get(id);
            if (type.isAssignableFrom(doGetType(d))) {
                return type.cast(doCreate(d));
            }
        }
        throw new ObjectNotFoundException(id, type);
    }

    @Override
    public <T> Set<T> getObjects(Class<T> type) {
        return getAllDefinitionsOfType(type).stream()
                .map(d -> type.cast(doCreate(d)))
                .collect(Collectors.toSet());
    }

    @Override
    public Set<String> getObjectIds() {
        return definitions.keySet();
    }

    @Override
    public Set<Class<?>> getObjectTypes() {
        return definitions.values().stream()
                .map(this::doGetType)
                .collect(Collectors.toSet());
    }

    @Override
    public Class<?> getType(String id) {
        if (definitions.containsKey(id)) {
            return doGetType(definitions.get(id));
        }
        throw new IdNotFoundException(id);
    }

    @Override
    public void registerObject(String id, D definition) {
        definitions.put(id, definition);
    }

    private List<D> getAllDefinitionsOfType(Class<?> type) {
        return definitions.values().stream()
                .filter(d -> type.isAssignableFrom(doGetType(d)))
                .collect(Collectors.toList());
    }
}
