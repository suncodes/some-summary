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

### 自动装配

spring的自动装配功能的定义：无须在Spring配置文件中描述javaBean之间的依赖关系（如配置<property>、<constructor-arg>）。IOC容器会自动建立javabean之间的关联关系。

#### Spring的自动装配Bean的三种方式

在Spring中，支持 5 自动装配模式。

- no – 缺省情况下，自动配置是通过“ref”属性手动设定
- byName – 根据属性名称自动装配。如果一个bean的名称和其他bean属性的名称是一样的，将会自装配它。
- byType – 按数据类型自动装配。如果一个bean的数据类型是用其它bean属性的数据类型，兼容并自动装配它。
- constructor – 在构造函数参数的byType方式。
- autodetect – 如果找到默认的构造函数，使用“自动装配用构造”; 否则，使用“按类型自动装配”。【在Spring3.0以后的版本被废弃，已经不再合法了】

1.手动装配

（1）创建一个单纯的实体类

```java
package suncodes.opensource.autowire.xml;

public class Address {

    private String fulladdress;

    public Address() {

    }

    public Address(String addr) {
        this.fulladdress = addr;
    }

    public String getFulladdress() {
        return fulladdress;
    }

    public void setFulladdress(String fulladdress) {
        this.fulladdress = fulladdress;
    }

    @Override
    public String toString() {
        return "Address{" +
                "fulladdress='" + fulladdress + '\'' +
                '}';
    }
}
```


（2）创建一个引用其他类的实体类

```java
package suncodes.opensource.autowire.xml;

public class Customer {

    private Address address;

    public Customer() {

    }

    public Customer(int id, Address address) {
        super();
        this.address = address;
    }

    public Address getAddress() {
        return address;
    }

    public void setAddress(Address address) {
        this.address = address;
    }

    @Override
    public String toString() {
        return "Customer{" +
                "address=" + address +
                '}';
    }
}
```

（3）创建配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="suncodes.opensource.autowire.xml.Address">
        <property name="fulladdress" value="河南省"/>
    </bean>
    <bean id="customer" class="suncodes.opensource.autowire.xml.Customer">
        <property name="address" ref="address"/>
    </bean>
</beans>
```

（4）测试

```java
public class AutowireTest {
    @Test
    public void autowireXmlNo() {
        ClassPathXmlApplicationContext context = new ClassPathXmlApplicationContext("autowire/AutowireXmlNo.xml");
        Customer customer = context.getBean("customer", Customer.class);
        System.out.println(customer);
    }
}
```

2.第一种自动装配【根据属性名称自动装配】

（1）创建一个单纯的实体类

（2）创建一个引用其他类的实体类

（3）创建配置文件

```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="suncodes.opensource.autowire.xml.Address">
        <property name="fulladdress" value="ByName"/>
    </bean>
    <bean id="customer" class="suncodes.opensource.autowire.xml.Customer" autowire="byName">
    </bean>
</beans>
```

这样就将address注入到Customer中。这就是自动注入ByName.在Customer bean中公开了一个属性address，Spring容器会找到address bean,并且装配。这里必须要注意，Customer中要被注入的bean的set方法要求必须是public的，否则会报空指针异常。还有配置的bean的id必须和Customer中声明的变量名相同。

3.第二种自动装配【根据数据类型自动装配】

配置文件
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="suncodes.opensource.autowire.xml.Address">
        <property name="fulladdress" value="ByType"/>
    </bean>
    <bean id="customer" class="suncodes.opensource.autowire.xml.Customer" autowire="byType">
    </bean>
</beans>
```
类型自动装配的意思是如果一个bean的数据类型与其他的bean属性的数据类型相同，将会自动兼容装配它。当然要求只能配置一个某一个类型的bean。

4.第三种自动装配【根据构造方法自动装配】

配置文件：
```xml
<?xml version="1.0" encoding="UTF-8"?>
<beans xmlns="http://www.springframework.org/schema/beans"
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
       xsi:schemaLocation="http://www.springframework.org/schema/beans http://www.springframework.org/schema/beans/spring-beans.xsd">

    <bean id="address" class="suncodes.opensource.autowire.xml.Address">
        <property name="fulladdress" value="constructor"/>
    </bean>
    <bean id="customer" class="suncodes.opensource.autowire.xml.Customer" autowire="constructor">
    </bean>
</beans>
```
需要有对应的构造函数，才可以。

#### @Autowired 、@Qualifier 和 @Primary

首先都需要声明成一个 Bean 才可以

@Autowired 注释，它可以对类成员变量、方法及构造函数进行标注，完成自动装配的工作。 通过 @Autowired的使用来消除 set ，get方法。

　　在使用@Autowired时，首先在容器中查询对应类型的bean

　　　　如果查询结果刚好为一个，就将该bean装配给@Autowired指定的数据

　　　　如果查询的结果不止一个，那么@Autowired会根据名称来查找。

　　　　如果查询的结果为空，那么会抛出异常。解决方法时，使用required=false

（1）按类型注入（默认）

    @Autowired
    private Address address;

（2）按名称注入（相同名称或@Qualifier）

使用Qualifier注解，选择一个对象的名称

    @Qualifier("addressByName")
    @Autowired
    private Address address;

（3）默认优先注入（@Primary）

Primary可以理解为默认优先选择,不可以同时设置多个,内部实质是设置BeanDefinition的primary属性

    @Bean
    Address address() {
        Address address = new Address();
        address.setFulladdress("另外一个address");
        return address;
    }

    @Bean
    Address addressByName() {
        Address address = new Address();
        address.setFulladdress("addressByName");
        return address;
    }

    @Primary
    @Bean
    Address addressByPrimary() {
        Address address = new Address();
        address.setFulladdress("addressByPrimary");
        return address;
    }

    @Component
    public class CustomerByPrimary {
    
        @Autowired
        private Address address;
    
        @Override
        public String toString() {
            return "Customer{" +
                    "address=" + address +
                    '}';
        }
    }


（4）@Primary和@Qualifier区别

- @Primary 在同一类型上只能设置一个
- 如果 @Qualifier 和 @Primary 注释都存在，那么 @Qualifier 注释将具有优先权。
- @Primary 注解是加在需要被引用的类上
- @Qualifier 是加在需要引用别人对象的属性或类或方法上

比如：Customer 依赖 Address，需要注入 Address

则 @Primary 需要加在多个 Address 上

@Qualifier 一般和 @Autowired 共用，加在同一个地方


#### @Resource

@Resource和@Autowired注解都是用来实现依赖注入的。只是@AutoWried按by type自动注入，而@Resource默认按byName自动注入。

@Resource有两个重要属性，分别是name和type

spring将name属性解析为bean的名字，而type属性则被解析为bean的类型。所以如果使用name属性，则使用byName的自动注入策略，如果使用type属性则使用byType的自动注入策略。如果都没有指定，则通过反射机制使用byName自动注入策略。

@Resource依赖注入时查找bean的规则：(以用在field上为例)

既不指定name属性，也不指定type属性，则自动按byName方式进行查找。如果没有找到符合的bean，则回退为一个原始类型进行查找，如果找到就注入。

（1）按类型注入（默认name）

    @Resource
    private Address address;

（2）按名称注入

@Qualifier 没效果

    @Resource(name = "addressByName")
    private Address address;

（3）按类型注入

    @Resource(type = Address.class)
    private Address address;

如果类型有多个，还是会按照名称注入

（4）默认优先注入（@Primary）

不起作用


#### @Inject

略

自动装配;
		Spring利用依赖注入（DI），完成对IOC容器中中各个组件的依赖关系赋值；

1）、@Autowired：自动注入：

		1）、默认优先按照类型去容器中找对应的组件:applicationContext.getBean(BookDao.class);找到就赋值
		2）、如果找到多个相同类型的组件，再将属性的名称作为组件的id去容器中查找
							applicationContext.getBean("bookDao")
		3）、@Qualifier("bookDao")：使用@Qualifier指定需要装配的组件的id，而不是使用属性名
		4）、自动装配默认一定要将属性赋值好，没有就会报错；
			可以使用@Autowired(required=false);
		5）、@Primary：让Spring进行自动装配的时候，默认使用首选的bean；
				也可以继续使用@Qualifier指定需要装配的bean的名字
		BookService{
			@Autowired
			BookDao  bookDao;
		}

2）、Spring还支持使用@Resource(JSR250)和@Inject(JSR330)[java规范的注解]

		@Resource:
			可以和@Autowired一样实现自动装配功能；默认是按照组件名称进行装配的；
			没有能支持@Primary功能没有支持@Autowired（reqiured=false）;
		@Inject:
			需要导入javax.inject的包，和Autowired的功能一样。没有required=false的功能；
 
        @Autowired:Spring定义的； @Resource、@Inject都是java规范



#### 自动装配原理

赋值：

org.springframework.beans.factory.support.AbstractAutowireCapableBeanFactory.populateBean

AutowiredAnnotationBeanPostProcessor 解析完成自动装配功能；

### Aware

Aware 接口，提供了类似回调函数的功能

自定义组件想要使用Spring 容器底层的一些组件（Application Context，Bean Factory）;自定义组件需要实现xxxAware接口；在创建对象的时候，会调用接口规定的方法注入相关组件

```java
package org.springframework.beans.factory;
public interface Aware {
}
```

#### 入门

1 ApplicationContextAware 自动注入IOC容器

```java
package org.springframework.context;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.Aware;

public interface ApplicationContextAware extends Aware {

    void setApplicationContext(ApplicationContext applicationContext) throws BeansException;

}
```

2 ApplicationEventPublisherAware 注入事件派发器

package org.springframework.context;

```java
import org.springframework.beans.factory.Aware;

public interface ApplicationEventPublisherAware extends Aware {

    void setApplicationEventPublisher(ApplicationEventPublisher applicationEventPublisher);

}
```

3 BeanClassLoaderAware 类加载器

```java
package org.springframework.beans.factory;

public interface BeanClassLoaderAware extends Aware {

    void setBeanClassLoader(ClassLoader classLoader);

}
```

4 BeanFactoryAware Bean工厂

```java
package org.springframework.beans.factory;

import org.springframework.beans.BeansException;
public interface BeanFactoryAware extends Aware {

    void setBeanFactory(BeanFactory beanFactory) throws BeansException;

}
```

5 BeanNameAware Bean名字

```java
package org.springframework.beans.factory;

public interface BeanNameAware extends Aware {

    void setBeanName(String name);

}
```

6 EmbeddedValueResolverAware Embedded值解析器

```java
package org.springframework.context;

import org.springframework.beans.factory.Aware;
import org.springframework.util.StringValueResolver;

public interface EmbeddedValueResolverAware extends Aware {

    void setEmbeddedValueResolver(StringValueResolver resolver);

}
```

7 EnvironmentAware 环境

```java
package org.springframework.context;

import org.springframework.beans.factory.Aware;
import org.springframework.core.env.Environment;

public interface EnvironmentAware extends Aware {

    void setEnvironment(Environment environment);

}
```

8 ImportAware 导入相关的

```java
package org.springframework.context.annotation;

import org.springframework.beans.factory.Aware;
import org.springframework.core.type.AnnotationMetadata;

public interface ImportAware extends Aware {

    void setImportMetadata(AnnotationMetadata importMetadata);

}
```

9 LoadTimeWeaverAware 导入相关的

```java
package org.springframework.context.weaving;

import org.springframework.beans.factory.Aware;
import org.springframework.instrument.classloading.LoadTimeWeaver;

public interface LoadTimeWeaverAware extends Aware {

    void setLoadTimeWeaver(LoadTimeWeaver loadTimeWeaver);

}
```

10 MessageSourceAware 国际化

```java
package org.springframework.context;

import org.springframework.beans.factory.Aware;

public interface MessageSourceAware extends Aware {

    void setMessageSource(MessageSource messageSource);

}
```

11 NotificationPublisherAware 发送通知的支持

```java
package org.springframework.jmx.export.notification;

import org.springframework.beans.factory.Aware;

public interface NotificationPublisherAware extends Aware {

    void setNotificationPublisher(NotificationPublisher notificationPublisher);

}
```

12 ResourceLoaderAware 资源加载器

```java
package org.springframework.context;

import org.springframework.beans.factory.Aware;
import org.springframework.core.io.ResourceLoader;

public interface ResourceLoaderAware extends Aware {

    void setResourceLoader(ResourceLoader resourceLoader);

}
```

13 测试用例

```java
@Component
public class MyAware implements ApplicationContextAware, BeanNameAware, EmbeddedValueResolverAware {

    private ApplicationContext applicationContext;

    @Override
    public void setBeanName(String name) {
        System.out.println("MyAware setBeanName:" + name);
    }

    @Override
    public void setApplicationContext(ApplicationContext applicationContext) throws BeansException {
        System.out.println("传入的IOC " + applicationContext);
        this.applicationContext = applicationContext;
    }

    @Override
    public void setEmbeddedValueResolver(StringValueResolver resolver) {
        String value = resolver.resolveStringValue("你好${os.name} 我是#{20*20}");
        System.out.println("解析的字符串：" + value);
    }
}

```

    MyAware setBeanName:myAware
    解析的字符串：你好Windows 10 我是400
    传入的IOC org.springframework.context.annotation.AnnotationConfigApplicationContext@4fccd51b, started on Tue Apr 13 16:47:02 CST 2021

#### 原理

### Profile

就是通过激活 spring.profiles.active 变量进行生效的

#### 入门

（1）创建实体类，模拟不同环境的不同配置

```java
public class MyProfile {
    private String env;

    public String getEnv() {
        return env;
    }

    public void setEnv(String env) {
        this.env = env;
    }

    @Override
    public String toString() {
        return "MyProfile{" +
                "env='" + env + '\'' +
                '}';
    }
}

```

（2）配置profile

```java
@ComponentScan(basePackages = "suncodes.opensource.profile")
@Configuration
public class MyProfileConfig {

    @Profile("dev")
    @Bean
    MyProfile myProfileDev() {
        MyProfile myProfile = new MyProfile();
        myProfile.setEnv("这是开发环境");
        return myProfile;
    }

    @Profile("test")
    @Bean
    MyProfile myProfileTest() {
        MyProfile myProfile = new MyProfile();
        myProfile.setEnv("这是测试环境");
        return myProfile;
    }
}

```

（3）测试（不配置spring.profiles.active）

```java
   @Test
    public void f() {
        AnnotationConfigApplicationContext context  =
                new AnnotationConfigApplicationContext(MyProfileConfig.class);
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        context.close();
    }
```

（4）测试（激活profile）

```java
    @Test
    public void f1() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        //1、创建一个applicationContext
        //2、设置需要激活的环境
        context.getEnvironment().setActiveProfiles("dev");
        //3、注册主配置类
        context.register(MyProfileConfig.class);
        //4、启动刷新容器
        context.refresh();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        context.close();
    }
```

#### Profile激活方式

（1）在配置文件中直接指定

    spring.profiles.active=test

（2）使用占位符，在打包时替换（maven）

    首先在配置文件中增加：
        spring.profiles.active=@package.target@
    在pom.xml中增加不同环境打包的配置：
       <profiles>
         <profile>
           <id>dev</id><!-- 开发环境 -->
         </profile>
         <profile>
           <id>prod</id><!-- 生产环境 -->
           <properties>
             <package.target>prod</package.target>
           </properties>
         </profile>
         <profile>
           <id>test</id><!-- 测试环境 -->
           <properties>
             <package.target>test</package.target>
           </properties>
         </profile>
       </profiles>
    
    执行打包命令：
    mvn package -Ptest  

（3）JVM参数方式

    java命令行：
      java -jar app.jar --spring.profiles.active=dev
    tomcat 中 catalina.bat（.sh中不用“set”） 添加JAVA_OPS。通过设置active选择不同配置文件：
      set JAVA_OPTS="-Dspring.profiles.active=test"
    eclipse 中启动tomcat。项目右键 run as –> run configuration–>Arguments–> VM arguments中添加。
      -Dspring.profiles.active="dev"


（4）web.xml方式

```xml
 <init-param>
   <param-name>spring.profiles.active</param-name>
   <param-value>production</param-value>
 </init-param>
```

（5）标注方式（junit单元测试非常实用）

    @ActiveProfiles({"unittest","productprofile"})

（6）Java API 方式

        // 1、创建一个applicationContext
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 2、设置需要激活的环境
        context.getEnvironment().setActiveProfiles("dev");
        // 3、注册主配置类
        context.register(MyProfileConfig.class);
        // 4、启动刷新容器
        context.refresh();

（7）添加到环境变量

```java
@WebListener
public class InitConfigListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        String environment = "";
        //加载Properties属性文件获取environment值 
        //侦测jvm环境，并缓存到全局变量中
        String env = System.setProperty("spring.profiles.active",environment);
    }
    @Override
    public void contextDestroyed(ServletContextEvent sce) {
    }
}
```

    在Unix/Linux环境中，可以通过环境变量注入profile的值：

    export spring_profiles_active=dev
    java -jar application.jar 


#### 和maven的profile的区别

https://blog.51cto.com/u_14254788/2419516

具体见 之前的 笔记 maven 一些标签总结

### AOP











































