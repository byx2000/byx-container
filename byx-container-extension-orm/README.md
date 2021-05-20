# byx-container-extension-orm

该项目为ByxContainer的ORM扩展，基于[ByxOrm](https://github.com/byx2000/byx-orm)。

## Maven引入

```xml
<repositories>
    <repository>
        <id>byx-maven-repo</id>
        <name>byx-maven-repo</name>
        <url>https://gitee.com/byx2000/maven-repo/raw/master/</url>
    </repository>
</repositories>

<dependencies>
    <dependency>
        <groupId>byx.ioc</groupId>
        <artifactId>byx-container-extension-orm</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## 使用方法

在Dao接口上标注`Dao`注解，表示该接口的实现类需要使用`ByxOrm`动态生成：

```java
@Dao
public interface UserDao {
    // ...
}
```

在其它组件内部，可直接使用`AutoWired`注入接口的实现类：

```java
@Autowired
private UserDao userDao;
```