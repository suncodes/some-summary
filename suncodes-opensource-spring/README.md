# 模块说明

此模块是用于 Spring 源码阅读的，通过一些代码，走读代码执行流程。

### Bean 的生命周期

**【注：此处都是说的单例，如果是多例，spring进行实例化之后，不再进行管理，不会执行这些初始化方法（包括BeanPostProcessor）以及销毁方法】**

#### @Bean(initMethod = "init", destroyMethod = "destroy")

1. 创建实体类

```java
package suncodes.opensource.lifecycle;

public class InitDestroyMethod {
    public void init() {
        System.out.println("InitDestroyMethod...init...");
    }

    public void destroy() {
        System.out.println("InitDestroyMethod...destroy...");
    }
}
```

2. 注册为一个 bean，并再注册的时候添加对应的 init-method 以及 destroy-method

```java
@Configuration
public class LifeCycleConfig {

    @Bean(initMethod = "init", destroyMethod = "destroy")
    InitDestroyMethod initDestroyMethod() {
        return new InitDestroyMethod();
    }
}
```

3. 测试

```java
public class Test {
    public static void main(String[] args) {
        // initMethod = "init" 被调用
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        // destroyMethod = "destroy" 被调用
        context.close();
    }
}
```

4. debug 调试

- 在 refresh() 里面打断点，之后看执行到哪一步会打印对应的初始化语句，发现在 finishBeanFactoryInitialization(beanFactory); 执行后会打印输出，说明此处是调用 init-method 方法的地方
- 之后进行方法，在 beanFactory.preInstantiateSingletons(); 处调用，实例化非懒加载的单实例Bean
- 在 preInstantiateSingletons 中，有两个循环，第一个循环，遍历所有的bean，进行实例化（init-method方法再次调用）
- 第一个循环的逻辑：
  - 获取对应的 Bean 的定义信息
  - 判断是否为 工厂 Bean （应该是实现了某个接口，暂略）
  -   之后就进入了 getBean 方法了，直接调用 doBean 进行实例化
  -   判断 之前是否 实例化过bean，如果是从缓存取出，如果没有，判断bean是否有继承等依赖，没有，判断是单实例还是多实例，单实例，调用 createBean 进行创建
  -   此处 创建实例，属性赋值，以及调用初始化方法（bean instanceof InitializingBean，以及init-method）



#### InitializingBean, DisposableBean

1. 创建实体类

```java
public class InitDestroyMethod implements InitializingBean, DisposableBean {
    @Override
    public void afterPropertiesSet() throws Exception {
        System.out.println("InitializingBean...afterPropertiesSet...");
    }

    @Override
    public void destroy() throws Exception {
        System.out.println("DisposableBean...destroy...");
    }
}
```

2. 配置成bean
3. 测试（见前面的代码）

4. 分析

- 其实从上次debug的时候就看到了 InitializingBean 初始化的地方，

org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#invokeInitMethods

boolean isInitializingBean = (bean instanceof InitializingBean);

((InitializingBean) bean).afterPropertiesSet();



#### @PostConstruct和@PreDestory

在实体类中的方法直接使用注解，表明这是初始化方法和销毁方法。

```java
@PostConstruct
public void postConstruct() {
    System.out.println("@PostConstruct...");
}

@PreDestroy
public void preDestroy() {
    System.out.println("@PreDestroy...");
}
```

分析：

在 org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory#initializeBean(java.lang.String, java.lang.Object, org.springframework.beans.factory.support.RootBeanDefinition) 的方法中 applyBeanPostProcessorsBeforeInitialization 中进行调用，此处在 invokeInitMethods（初始化 InitializingBean 和 @Bean(initMethod = "init")）之前调用。



#### 实现 BeanPostProcessor 接口

一种错误的做法：

（1）实现一个 BeanPostProcessor 接口

（2）通过 @Bean 的方式进行注入容器

这种方式会报错：

BeanPostProcessorChecker这个后置处理器输出了一句 xxx is not eligible for getting processed by all BeanPostProcessors (for example: not eligible for auto-proxying)这样的日志

https://blog.csdn.net/f641385712/article/details/89737791

具体原因：

`BeanPostProcessor`本身也是一个Bean，一般而言其实例化时机要早过普通的Bean，但是`BeanPostProcessor`有时也会依赖一些Bean，这就导致了一些普通Bean的实例化早于`BeanPostProcessor`的可能情况，由此如果使用不当，就会造成一些问题

【注意】BeanPostProcessor 会为所有的 我们自定义的 bean ，进行初始化前后的处理，不只是单单对其中一个Bean进行处理。所以如果我们自定义的Bean的初始化早于我们的 BeanPostProcessor ，则会有问题。

正确做法：

1. 创建 BeanPostProcessor 实现类

```java
package suncodes.opensource.lifecycle;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.BeanPostProcessor;
import org.springframework.stereotype.Component;

/**
 * 后置处理器：初始化前后进行处理工作
 * 将后置处理器加入到容器中
 *
 * @author lfy
 */
@Component
public class MyBeanPostProcessor implements BeanPostProcessor {

    @Override
    public Object postProcessBeforeInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessBeforeInitialization..." + beanName + "=>" + bean);
        return bean;
    }

    @Override
    public Object postProcessAfterInitialization(Object bean, String beanName) throws BeansException {
        System.out.println("postProcessAfterInitialization..." + beanName + "=>" + bean);
        return bean;
    }
}
```

2. 添加扫描配置

```
@ComponentScan(basePackageClasses = {MyBeanPostProcessor.class})
```

3. 测试，打印结果

```
postProcessBeforeInitialization...lifeCycleConfig=>suncodes.opensource.lifecycle.LifeCycleConfig$$EnhancerBySpringCGLIB$$7df1300e@5a4aa2f2
postProcessAfterInitialization...lifeCycleConfig=>suncodes.opensource.lifecycle.LifeCycleConfig$$EnhancerBySpringCGLIB$$7df1300e@5a4aa2f2
postProcessBeforeInitialization...initDestroyMethod=>suncodes.opensource.lifecycle.InitDestroyMethod@1a052a00
@PostConstruct...
InitializingBean...afterPropertiesSet...
InitDestroyMethod...init...
postProcessAfterInitialization...initDestroyMethod=>suncodes.opensource.lifecycle.InitDestroyMethod@1a052a00
@PreDestroy...
DisposableBean...destroy...
InitDestroyMethod...destroy1...
```

可以发现对所有的 Bean 都进行了回调。

### 属性赋值

有三种方式：
- @Value
- @PropertySource
- StringValueResolver（略）

基本都是配合使用的

1. 创建实体类

```java
package suncodes.opensource.propertyvalue;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.PropertySource;
import org.springframework.stereotype.Component;

@Component
@PropertySource("classpath:propertyValue.properties")
public class Person {

    /**
     * 使用@Value赋值；
     * 1、基本数值
     * 2、可以写SpEL； #{}
     * 3、可以写${}；取出配置文件【properties】中的值（在运行环境变量里面的值）
     */
    @Value("张三")
    private String name;
    @Value("#{20-2}")
    private Integer age;

    @Value("${person.nickName}")
    private String nickName;

    public String getNickName() {
        return nickName;
    }

    public void setNickName(String nickName) {
        this.nickName = nickName;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Person(String name, Integer age) {
        super();
        this.name = name;
        this.age = age;
    }

    public Person() {
        super();
    }

    @Override
    public String toString() {
        return "Person [name=" + name + ", age=" + age + ", nickName=" + nickName + "]";
    }
}

```

2. 创建配置文件

```properties
# propertyValue.properties
person.nickName="hehe"
person.other="aaaaaaaaaaa"
```
3. 扫描类

```java
package suncodes.opensource.propertyvalue;

import org.springframework.context.annotation.ComponentScan;

@ComponentScan(basePackageClasses = {Person.class})
public class PropertyValueConfig {
}

```
4. 测试类
```java
package suncodes.opensource.propertyvalue;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertyValueConfig.class);
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
        context.close();
    }
}

```




































