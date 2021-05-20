# ByxContainer声明式事务扩展

该扩展需要配合[ByxContainerAnnotation](https://github.com/byx2000/byx-container-annotation)一起使用。

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
        <artifactId>byx-container-extension-transaction</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## 使用步骤

1. 引入JDBC和数据库连接池相关依赖，添加配置类，注册`DataSource`

    ```java
    @Component
    public class DbConfig {
        @Component
        public DataSource dataSource() {
            // 返回一个DataSource
            // ...
        }
    }
    ```

2. 在`Dao`中注入`JdbcUtils`，使用`JdbcUtils`操作数据库

    ```java
    @Component
    public class MyDao {
        @Autowired
        private JdbcUtils jdbcUtils;
        
        public void dao1() {
            jdbcUtils.update("update A set value = 90");
        }
        
        public void dao2() {
            jdbcUtils.update("update B set value = 10");
        }
    }
    ```

    关于`JdbcUtils`的详细使用，请看[这里](https://github.com/byx2000/JdbcUtils)。

3. 在`Service`中注入`Dao`，在希望开启事务的`Service`方法上标注`Transactional`注解

    ```java
    @Component
    public class MyService {
        @Autowired
        private MyDao myDao;

        @Transactional
        public void service() {
            myDao.dao1();
            // 业务逻辑
            // ...
            myDao.dao2();
        }
    }
    ```

    关于声明式事务的其它细节，请看[这里](https://github.com/byx2000/byx-transaction)。

4. 通过容器获取`MyService`并使用

   ```java
   Container container = ...;
   MyService service = container.getObject(MyService.class);

   // 使用service
   // ...
   ```