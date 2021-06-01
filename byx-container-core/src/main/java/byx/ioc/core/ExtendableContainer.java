package byx.ioc.core;

import byx.ioc.exception.LoadJarResourceException;
import byx.ioc.util.JarUtils;
import byx.ioc.util.OrderUtils;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;
import java.util.ServiceLoader;

/**
 * 可扩展的容器
 * 该容器初始化时会加载以下扩展组件：
 * ObjectCallback、ContainerCallback、ValueConverter、额外导入的组件
 *
 * @author byx
 */
public abstract class ExtendableContainer implements Container {
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
    private final static List<Class<?>> IMPORT_COMPONENTS;

    static {
        // 加载扩展组件
        OBJECT_CALLBACKS = OrderUtils.sort(loadObjectCallbacks());
        CONTAINER_CALLBACKS = OrderUtils.sort(loadContainerCallbacks());
        VALUE_CONVERTERS = loadValueConverters();

        // 加载使用SPI导入的类
        IMPORT_COMPONENTS = loadImportComponents();
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
    private static List<Class<?>> loadImportComponents() {
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
     * 获取所有ObjectCallback
     * @return ObjectCallback列表
     */
    protected List<ObjectCallback> getObjectCallbacks() {
        return OBJECT_CALLBACKS;
    }

    /**
     * 获取所有ContainerCallback
     * @return ContainerCallback列表
     */
    protected List<ContainerCallback> getContainerCallbacks() {
        return CONTAINER_CALLBACKS;
    }

    /**
     * 获取所有ValueConverter
     * @return ValueConverter列表
     */
    protected List<ValueConverter> getValueConverters() {
        return VALUE_CONVERTERS;
    }

    /**
     * 获取所有额外导入的组件
     * @return 组件列表
     */
    protected List<Class<?>> getImportComponents() {
        return IMPORT_COMPONENTS;
    }
}
