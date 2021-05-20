# byx-container-extension-aop

该项目为ByxContainer的AOP扩展，基于[ByxAOP](https://github.com/byx2000/ByxAOP)。

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
        <artifactId>byx-container-extension-aop</artifactId>
        <version>1.0.0</version>
    </dependency>
</dependencies>
```

## 使用步骤

1. 声明一个切面类

    ```java
    @Component
    public class Advice {
        @Before
        public void before() {
            System.out.println("before");
        }

        @After
        public void after() {
            System.out.println("after");
        }
    }
    ```

    注意，切面类必须要标注`Component`注解。

2. 声明一个组件，并使用`AdviceBy`指定切面类

    ```java
    @Component
    @AdviceBy(Advice.class)
    public class A {
        public void f() {
            System.out.println("f");
        }

        public void g() {
            System.out.println("g");
        }
    }
    ```

3. 从容器获取`A`，类`A`的对象会被指定的切面类增强

    ```java
    Container container = ...;
    A a = container.getObject(A.class);
    a.f();
    a.g();
    ```

    控制台输出如下：

    ```java
    before
    f
    after
    before
    g
    after
    ```

    可以看到，`A`的`f`和`g`方法都被增强了。