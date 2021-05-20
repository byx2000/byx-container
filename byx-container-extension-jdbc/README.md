# byx-container-extension-jdbc

该项目为ByxContainer的JDBC扩展，基于[JdbcUtils](https://github.com/byx2000/JdbcUtils)。

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
        <artifactId>byx-container-extension-jdbc</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## 使用方法

引入该项目后，可以直接使用`AutoWired`注入`JdbcUtils`：

```java
@Autowired
private JdbcUtils jdbcUtils;
```