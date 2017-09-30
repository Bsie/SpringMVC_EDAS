test02:
学习使用阿里云的hsf，总结并记录与之关联的关键点，以及小白版的操作使用步骤。
目标：用maven搭建一个基于springMVC的项目，使用到hsf基本的技术

---
## HSF（High Speed Framework）
> 高可用、高性能、分布式的服务框架的RPC服务框架。帮助应用轻松实现服务化解耦，是阿里内部各个系统通信的基础软件。为EDAS应用开发提供了一套分布式服务框架解决方案，更容易开发分布式应用。

```
graph LR
HSF_App-->Pandora 
Pandora-->Ali_Tomcat

```

#### HSF项目开发
##### 定义服务接口
> HSF 的服务基于++接口++实现，当接口定义好之后，生产者将通过该接口以实现具体的服务，消费者也是基于此接口作为服务去订阅。

```
public interface ItemService {
    public Item getItemById(long id);
    public Item getItemByName(String name);
}
```
打包成jar包，放到Maven中心仓库中，供使用者调用。
##### 服务者 
- 代码实现服务接口

```
package com.alibaba.edas.carshop.itemcenter;
public class ItemServiceImpl implements ItemService {
    @Override
    public Item getItemById( long id ) {
        Item car = new Item();
        car.setItemId( 1l );
        car.setItemName( "Mercedes Benz" );
        return car;
    }
    @Override
    public Item getItemByName( String name ) {
        Item car = new Item();
        car.setItemId( 1l );
        car.setItemName( "Mercedes Benz" );
        return car;
    }
}
```

- 服务配置
- 1.  Maven依赖

```
pom.xml
<dependencies>
     <dependency>
         <groupId>javax.servlet</groupId>
         <artifactId>servlet-api</artifactId>
         <version>2.5</version>
         <scope>provided</scope>
     </dependency>
     <dependency>
         <groupId>com.alibaba.edas.carshop</groupId>
         <artifactId>itemcenter-api</artifactId>
         <version>1.0.0-SNAPSHOT</version>
     </dependency>
     <dependency>
         <groupId>org.springframework</groupId>
         <artifactId>spring-web</artifactId>
         <version>2.5.6(及其以上版本)</version>
     </dependency>
     <!-- pom 中添加edas-sdk依赖-->
     <dependency>
         <groupId>com.alibaba.edas</groupId>
        <artifactId>edas-sdk</artifactId>
         <version>1.5.0</version>
     </dependency>
</dependencies>
```

- 2. 增加配置文件中关于 HSF 服务的配置（使用 <hsf /> 标签注册并发布该服务）

```
hsf-provider-beans.xml 
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
     xmlns:hsf="http://www.taobao.com/hsf"
     xmlns="http://www.springframework.org/schema/beans"
     xsi:schemaLocation="http://www.springframework.org/schema/beans
     http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
     http://www.taobao.com/hsf
     http://www.taobao.com/hsf/hsf.xsd" default-autowire="byName">
     <!-- 定义实现该服务的具体实现 -->
     <bean id="itemService" class="com.alibaba.edas.carshop.itemcenter.ItemServiceImpl" />
     <!-- 用 hsf:provider 标签表明提供一个服务生产者 -->
     <hsf:provider id=“itemServiceProvider"
         <!-- 用 interface 属性说明该服务为此类的一个实现 -->
         interface=“com.alibaba.edas.carshop.itemcenter.ItemService"
         <!-- 此服务具体实现的 spring 对象 -->
         ref=“itemService"
         <!-- 发布该服务的版本号，可任意指定，默认为 1.0.0 -->
         version=“1.0.0"
         <!-- 服务分组 -->
         group=“testHSFGroup-09-04“>
     </hsf:provider>
</beans>
```
###### Provider配置文件示例 ：
```
<bean id="impl" class="com.cj.test.service.impl.SimpleServiceImpl>
    <hsf:provide  id="simpleService" interface="com.cj.test.servuce.SimpleService" ref="impl" version="1.0.0" group="test" clientTimeout="3000" >
    </hsf:provide>
</bean>
```

##### 消费者 
- 订阅 
- 1. Maven依赖 （参考消费者）
- 2. 增加 Spring 关于消费者的定义

```
hsf-consumer-beans.xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
        xmlns:hsf="http://www.taobao.com/hsf"
        xmlns="http://www.springframework.org/schema/beans"
        xsi:schemaLocation="http://www.springframework.org/schema/beans
        http://www.springframework.org/schema/beans/spring-beans-2.5.xsd
        http://www.taobao.com/hsf
        http://www.taobao.com/hsf/hsf.xsd" default-autowire="byName">
        <!-- 消费一个服务示例 -->
        <hsf:consumer
            <!-- Bean ID，在代码中可根据此ID进行注入并使用  -->
            id="item"
            <!-- 服务名，与服务提供者的相应配置对应，HSF 将根据 interface + version + group 查询并订阅所需服务  -->
            interface="com.alibaba.edas.carshop.itemcenter.ItemService"
            <!-- 版本号，与服务提供者的相应配置对应，HSF 将根据 interface + version + group 查询并订阅所需服务  -->
            version="1.0.0"
            <!-- 分组名  -->
            group="testHSFGroup">
        </hsf:consumer>
</beans>
```
- 消费者使用服务

```
public class StartListener implements ServletContextListener{
    @Override
    public void contextInitialized( ServletContextEvent sce ) {
        ApplicationContext ctx = WebApplicationContextUtils.getWebApplicationContext( sce.getServletContext() );
        // 根据 Spring 配置中的Bean ID “item” 获取订阅到的服务
        final ItemService itemService = ( ItemService ) ctx.getBean( "item" );
        ……
        // 调用服务 ItemService 的 getItemById 方法
        System.out.println( itemService.getItemById( 1111 ) );
        // 调用服务 ItemService 的 getItemByName 方法
        System.out.println( itemService.getItemByName( "myname is le" ) );
        ……
    }
}
```

###### Consumer配置文件示例 ：
```
<hsf:consumer  id="service" interface="com.cj.test.servuce.SimpleService"      version="1.0.0" group="test" clientTimeout="3000" >
</hsf:consumer >
```
##### 服务中心
 完成开发后，以Ali-Tomcat运行服务，并在搭建好的ConfigCenter中查询所需要消费的服务。
