package byx.ioc.annotation.callback;

import byx.ioc.annotation.annotation.Autowired;
import byx.ioc.annotation.annotation.Component;
import byx.ioc.annotation.annotation.Id;
import byx.ioc.annotation.annotation.Import;
import byx.ioc.annotation.core.AnnotationConfigContainerCallback;
import byx.ioc.annotation.core.AnnotationConfigContainerContext;
import byx.ioc.annotation.core.ObjectDefinition;
import byx.ioc.annotation.exception.CannotRegisterInterfaceException;
import byx.ioc.annotation.exception.ConstructorMultiDefException;
import byx.ioc.annotation.exception.ConstructorNotFoundException;
import byx.ioc.util.ClassPredicates;
import byx.ioc.annotation.util.PackageScanner;
import byx.ioc.core.Container;
import byx.ioc.core.Dependency;
import byx.ioc.util.ExtensionLoader;

import java.lang.reflect.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 处理Component注解
 *
 * @author byx
 */
public class ComponentProcessor implements AnnotationConfigContainerCallback {
    @Override
    public void afterAnnotationConfigContainerInit(AnnotationConfigContainerContext ctx) {
        // 扫描来源如下;
        // 1. 包下所有标注了Component注解的类
        // 2. 所有标注了Component注解的扩展类
        // 3. 使用Import注解导入的类
        List<Class<?>> classes = new ArrayList<>(ExtensionLoader.getExtensionsWithAnnotation(Component.class));
        PackageScanner scanner = ctx.getPackageScanner();
        scanner.getClasses(ClassPredicates.hasAnnotation(Import.class)).stream()
                .flatMap(c -> Arrays.stream(c.getAnnotation(Import.class).value().clone()))
                .forEach(classes::add);
        classes.addAll(scanner.getClasses(ClassPredicates.hasAnnotation(Component.class)));
        classes.forEach(c -> processClass(c, ctx));
    }

    /**
     * 处理被Component标注的类
     */
    private void processClass(Class<?> type, AnnotationConfigContainerContext ctx) {
        // 获取id
        String id = type.isAnnotationPresent(Id.class)
                ? type.getAnnotation(Id.class).value()
                : type.getCanonicalName();

        if (type.isInterface()) {
            throw new CannotRegisterInterfaceException(type);
        }

        // 获取实例化的构造函数
        Constructor<?> constructor = getConstructor(type);

        // 获取实例化的依赖项
        Dependency[] dependencies = getParameterDependencies(constructor);

        // 创建ObjectDefinition
        ObjectDefinition definition = new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return type;
            }

            @Override
            public Dependency[] getDependencies() {
                return dependencies;
            }

            @Override
            public Object getInstance(Object[] dependencies) {
                try {
                    return constructor.newInstance(dependencies);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // 注册对象
        ctx.getObjectRegistry().registerObject(id, definition);

        // 处理方法
        Arrays.stream(type.getMethods())
                .filter(m -> m.isAnnotationPresent(Component.class))
                .forEach(m -> processMethod(type, m, ctx));
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
     * 获取参数的id
     */
    private String getId(AnnotatedElement element) {
        if (element.isAnnotationPresent(Id.class)) {
            return element.getAnnotation(Id.class).value();
        }
        return null;
    }

    /**
     * 获取参数依赖项
     */
    private Dependency[] getParameterDependencies(Executable executable) {
        Parameter[] parameters = executable.getParameters();
        Class<?>[] types = Arrays.stream(parameters).map(Parameter::getType).toArray(Class<?>[]::new);
        String[] ids = Arrays.stream(parameters).map(this::getId).toArray(String[]::new);
        return getDependencies(types, ids);
    }

    /**
     * 处理被Component标注的方法
     */
    private void processMethod(Class<?> instanceType, Method method, AnnotationConfigContainerContext ctx) {
        // 解析依赖项
        Dependency[] dependencies = getParameterDependencies(method);

        Container container = ctx.getContainer();

        // 创建ObjectDefinition
        ObjectDefinition definition = new ObjectDefinition() {
            @Override
            public Class<?> getType() {
                return method.getReturnType();
            }

            @Override
            public Dependency[] getDependencies() {
                return dependencies;
            }

            @Override
            public Object getInstance(Object[] dependencies) {
                Object instance = (instanceType.isAnnotationPresent(Id.class))
                        ? container.getObject(instanceType.getAnnotation(Id.class).value())
                        : container.getObject(instanceType);
                try {
                    method.setAccessible(true);
                    return method.invoke(instance, dependencies);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }
        };

        // 获取id
        String id = method.isAnnotationPresent(Id.class)
                ? method.getAnnotation(Id.class).value()
                : method.getName();

        ctx.getObjectRegistry().registerObject(id, definition);
    }

    private Dependency[] getDependencies(Class<?>[] paramTypes, String[] paramIds) {
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
}
