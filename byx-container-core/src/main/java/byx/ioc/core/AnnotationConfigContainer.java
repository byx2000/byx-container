package byx.ioc.core;

import byx.ioc.annotation.*;
import byx.ioc.exception.ConstructorMultiDefException;
import byx.ioc.exception.ConstructorNotFoundException;
import byx.ioc.exception.ValueConverterNotFoundException;
import byx.ioc.util.AnnotationScanner;

import java.lang.annotation.Annotation;
import java.lang.reflect.Constructor;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 通过注解来配置的容器
 *
 * @author byx
 */
public class AnnotationConfigContainer extends AbstractContainer {
    /**
     * 创建一个AnnotationConfigContainer
     *
     * @param basePackage 基准包名
     */
    public AnnotationConfigContainer(String basePackage) {
        new AnnotationScanner(basePackage)
                .getClassesAnnotatedBy(Component.class)
                .forEach(this::processClass);
        afterContainerInit();
    }

    /**
     * 创建一个AnnotationConfigContainer
     *
     * @param baseClass 基准类
     */
    public AnnotationConfigContainer(Class<?> baseClass) {
        this(baseClass.getPackageName());
    }

    /**
     * 处理被Component标注的类
     */
    private void processClass(Class<?> type) {
        // 获取实例化的构造函数
        Constructor<?> constructor = getConstructor(type);

        // 获取实例化的依赖项
        Dependency[] dependencies = processDependencies(constructor);

        // 创建ObjectDefinition
        ObjectDefinition definition = new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return type;
            }

            @Override
            public Dependency[] getInstanceDependencies() {
                return dependencies;
            }

            @Override
            public Object getInstance(Object[] params) {
                try {
                    return constructor.newInstance(params);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // 获取id
        String id = type.isAnnotationPresent(Id.class)
                ? type.getAnnotation(Id.class).value()
                : type.getCanonicalName();

        // 注册对象
        registerObject(id, definition);

        // 处理Value注解
        processValue(type);

        // 处理方法
        Arrays.stream(type.getMethods())
                .filter(m -> m.isAnnotationPresent(Component.class))
                .forEach(m -> processMethod(type, m));
    }

    /**
     * 获取用于对象实例化的构造函数
     */
    private Constructor<?> getConstructor(Class<?> type) {
        Constructor<?>[] constructors = type.getConstructors();
        Constructor<?> constructor;

        // 如果只有唯一的构造方法，则直接使用这个构造方法
        // 如果有多个构造方法，则使用标注了Autowired注解的构造方法
        // 其它情况则报错
        if (constructors.length == 1) {
            constructor = constructors[0];
        } else {
            constructors = Arrays.stream(constructors)
                    .filter(c -> c.isAnnotationPresent(Autowired.class))
                    .toArray(Constructor[]::new);
            if (constructors.length == 0) {
                throw new ConstructorNotFoundException(type);
            } else if (constructors.length > 1) {
                throw new ConstructorMultiDefException(type);
            } else {
                constructor = constructors[0];
            }
        }

        return constructor;
    }

    /**
     * 构造函数依赖项的获取
     */
    private Dependency[] processDependencies(Constructor<?> constructor) {
        // 获取构造函数参数的注入类型
        Class<?>[] paramTypes = constructor.getParameterTypes();

        // 获取构造函数参数的注入id
        Annotation[][] paramAnnotations = constructor.getParameterAnnotations();
        String[] paramIds = new String[paramTypes.length];
        for (int i = 0; i < paramTypes.length; ++i) {
            for (Annotation a : paramAnnotations[i]) {
                if (a instanceof Id) {
                    paramIds[i] = ((Id) a).value();
                }
            }
        }

        // 解析依赖项
        Dependency[] dependencies = new Dependency[paramTypes.length];
        for (int i = 0; i < dependencies.length; ++i) {
            if (paramIds[i] != null) {
                dependencies[i] = Dependency.id(paramIds[i]);
            } else {
                dependencies[i] = Dependency.type(paramTypes[i]);
            }
        }

        return dependencies;
    }

    /**
     * 处理被Component标注的方法
     */
    private void processMethod(Class<?> instanceType, Method method) {
        // 获取方法参数注入类型
        Class<?>[] paramTypes = method.getParameterTypes();
        Annotation[][] paramAnnotations = method.getParameterAnnotations();

        // 获取方法参数注入id
        String[] paramIds = new String[paramTypes.length];
        for (int i = 0; i < paramTypes.length; ++i) {
            for (Annotation a : paramAnnotations[i]) {
                if (a instanceof Id) {
                    paramIds[i] = ((Id) a).value();
                }
            }
        }

        // 获取方法所属的对象实例id
        String instanceId = instanceType.isAnnotationPresent(Id.class)
                ? instanceType.getAnnotation(Id.class).value()
                : null;

        // 解析依赖项
        Dependency[] dependencies = new Dependency[paramTypes.length];
        for (int i = 0; i < dependencies.length; ++i) {
            if (paramIds[i] != null) {
                dependencies[i] = Dependency.id(paramIds[i]);
            } else {
                dependencies[i] = Dependency.type(paramTypes[i]);
            }
        }

        // 创建ObjectDefinition
        ObjectDefinition definition = new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return method.getReturnType();
            }

            @Override
            public Dependency[] getInstanceDependencies() {
                return dependencies;
            }

            @Override
            public Object getInstance(Object[] params) {
                Object instance = (instanceId == null)
                        ? AnnotationConfigContainer.this.getObject(instanceType)
                        : AnnotationConfigContainer.this.getObject(instanceId);
                try {
                    method.setAccessible(true);
                    return method.invoke(instance, params);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // 获取id
        String id = method.isAnnotationPresent(Id.class)
                ? method.getAnnotation(Id.class).value()
                : method.getName();

        registerObject(id, definition);
    }

    /**
     * 处理Value注解
     */
    private void processValue(Class<?> type) {
        List<Value> values = new ArrayList<>();
        if (type.isAnnotationPresent(Value.class)) {
            values.add(type.getAnnotation(Value.class));
        } else if (type.isAnnotationPresent(Values.class)) {
            values = Arrays.asList(type.getAnnotation(Values.class).value().clone());
        }

        for (Value v : values) {
            ValueConverter converter = getValueConverter(String.class, v.type());
            if (converter == null) {
                throw new ValueConverterNotFoundException(v.type());
            }
            String id = "".equals(v.id()) ? v.value() : v.id();
            registerObject(id, new ObjectDefinition() {
                @Override
                public Class<?> getType() {
                    return v.type();
                }

                @Override
                public Object getInstance(Object[] params) {
                    return converter.convert(v.value());
                }
            });
        }
    }
}
