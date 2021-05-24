# ByxContainer

ByxContainer是一个模仿Spring IOC设计的轻量级IOC容器，支持以下特性：

* 构造函数注入、setter注入等多种注入方式
* 自定义对象的初始化方法
* 循环依赖检测和处理
* 完善的扩展机制

该项目包含以下子项目：

|子项目|描述|
|---|---|
|[byx-container-core](https://github.com/byx2000/byx-container/tree/master/byx-container-core)|ByxContainer核心库|
|[byx-container-annotation](https://github.com/byx2000/byx-container/tree/master/byx-container-annotation)|基于注解的ByxContainer实现|
|[byx-container-extension-aop](https://github.com/byx2000/byx-container/tree/master/byx-container-extension-aop)|ByxContainer的AOP扩展|
|[byx-container-extension-jdbc](https://github.com/byx2000/byx-container/tree/master/byx-container-extension-jdbc)|ByxContainer的JDBC扩展|
|[byx-container-extension-orm](https://github.com/byx2000/byx-container/tree/master/byx-container-extension-orm)|ByxContainer的ORM扩展|
|[byx-container-extension-transaction](https://github.com/byx2000/byx-container/tree/master/byx-container-extension-transaction)|ByxContainer的声明式事务扩展|