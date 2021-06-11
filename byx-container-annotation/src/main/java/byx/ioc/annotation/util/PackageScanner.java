package byx.ioc.annotation.util;

import byx.ioc.annotation.exception.PackageScanException;

import java.io.File;
import java.net.URI;
import java.util.*;
import java.util.function.Predicate;
import java.util.stream.Collectors;

/**
 * 包扫描器
 *
 * @author byx
 */
public class PackageScanner {
    private static final String CLASS_FILE_SUFFIX = ".class";

    private final Set<Class<?>> classes = new HashSet<>();

    /**
     * 创建PackageScanner
     *
     * @param baseClass 包中的类
     */
    public PackageScanner(Class<?> baseClass) {
        this(baseClass.getPackageName());
    }

    /**
     * 创建PackageScanner
     *
     * @param packageName 包名
     */
    public PackageScanner(String packageName) {
        try {
            String packagePath = packageName.replace(".", File.separator);
            URI uri = Objects.requireNonNull(Thread.currentThread().getContextClassLoader().getResource("")).toURI();
            File classpath = new File(uri);
            File basePath = new File(classpath, packagePath);
            traverseAllClassFiles(classpath.getAbsolutePath(), basePath);
        } catch (Exception e) {
            throw new PackageScanException(e);
        }
    }

    /**
     * 遍历目录下的所有class文件
     */
    private void traverseAllClassFiles(String classPath, File file) {
        if (!file.isDirectory()) {
            if (file.getName().endsWith(CLASS_FILE_SUFFIX)) {
                String classFilePath = file.getAbsolutePath();
                String fullClassName = classFilePath.substring(classPath.length() + 1, classFilePath.length() - 6).replace(File.separator, ".");
                try {
                    Class<?> c = Class.forName(fullClassName);
                    classes.add(c);
                } catch (ClassNotFoundException e) {
                    throw new PackageScanException(e);
                }
            }
            return;
        }

        for (File f : Objects.requireNonNull(file.listFiles())) {
            traverseAllClassFiles(classPath, f);
        }
    }

    /**
     * 获取包中的类
     *
     * @param predicate 条件
     * @return 符合条件的类
     */
    public Set<Class<?>> getClasses(Predicate<Class<?>> predicate) {
        return classes.stream().filter(predicate).collect(Collectors.toSet());
    }
}
