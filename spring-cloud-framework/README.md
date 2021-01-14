# spring-cloud-framework

####介绍
*本项目出于闲暇时间弄的，不定期更新。若您觉得对于您的学习有帮助，烦请点个star支持*

**clone本项目建议idea下载sonarLint代码检测插件，这样可看出来本项目存在代码需要优化的地方，
看黄线和红线的地方。同时也可以查看自己的项目潜在的代码写法隐患等**


### 软件架构
![image](https://github.com/markyangchangliang/framework/blob/main/spring-cloud-framework/pojo/src/main/java/com/markyang/framework/pojo/img/20200404230333236.png)
软件架构说明
**项目采用spring cloud搭建**
**Eureka做服务注册中心**
**zuul 做网关和限流**
**Hystrix做熔断处理**
**spring security做安全框架**
**（配置中心正在规划）**
**基于 Ribbon实现客户端的负载均衡，服务消费者直接通过调用被@LoadBalanced注解修饰过的RestTemplate来实现面向服务的接口调用**
**feign做跨服务调用**
**集成redis做缓存，做全局缓存处理**
**redission做分布式幂等一致性，雪花算法做全局id生成**
**rabbitMQ做消息分发异步解耦**
**针对mybatis-plus做一部分拓展使得运用更加方便和简洁（目前正在做JPA版本的），实现plus增强，可实现额外条件构造**
**应用全局钩子定义更新、添加、查询、删除分别前后的处理，并可自定义实现，做全局容错处理，做全局字典翻译处理，添加注解可实现字典自动翻译。**
**框架层面controller基本增删改查方法处理，实现框架层面controller除和特殊的逻辑外不需要再写基本的增删改查方法，实现beffor或affter方法做自定义逻辑和改造**
**集成微信支付、腾讯云短信发送，集成quartz做框架层面定时器（已摘除）。**
**common-util中集成常用的反射工具类以及其他的常用工具类**

#### 安装教程

1.  clone或fork本项目之后，修改配置文件，添加数据库基本表
2.  本地启动redis和rabbitMQ之后，本地可直接启动，优先启动eureka，而后启动网关和认证中心，其他随意。
3.  部署采用的是docker部署或自定义部署，不会的自行百度或参考------

#### 使用说明

1.  后期补上各种方法的使用文档，现在将就着看下代码调用
2.  xxxx
3.  xxxx

#### 参与贡献

1.  Fork 本仓库
2.  新建 Feat_xxx 分支
3.  提交代码
4.  新建 Pull Request


#### 特技

1.  使用 Readme\_XXX.md 来支持不同的语言，例如 Readme\_en.md, Readme\_zh.md
