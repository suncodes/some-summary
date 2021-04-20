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

#### 入门

（1）pom文件添加依赖

```xml
        <!-- @Aspect 注解依赖 -->
        <dependency>
            <groupId>org.aspectj</groupId>
            <artifactId>aspectjweaver</artifactId>
            <version>1.9.6</version>
        </dependency>
```

（2）目标类

```java
package suncodes.opensource.aop;

public class MathCalculator {

    public int div(int i, int j) {
        System.out.println("MathCalculator...div...");
        return i / j;
    }
}

```

（3）切面类

```java
package suncodes.opensource.aop;

import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.After;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.AfterThrowing;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Before;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Arrays;

/**
 * 切面类
 *
 * @Aspect： 告诉Spring当前类是一个切面类
 */
@Aspect
public class LogAspects {

    /**
     * 抽取公共的切入点表达式
     * 1、本类引用
     * 2、其他的切面引用
     */
    @Pointcut("execution(public int suncodes.opensource.aop.MathCalculator.*(..))")
    public void pointCut() {
    }

    /**
     * - @Before在目标方法之前切入；切入点表达式（指定在哪个方法切入）
     */
    @Before("pointCut()")
    public void logStart(JoinPoint joinPoint) {
        Object[] args = joinPoint.getArgs();
        System.out.println("" + joinPoint.getSignature().getName() +
                "运行。。。@Before:参数列表是：{" + Arrays.asList(args) + "}");
    }

    @After("pointCut()")
    public void logEnd(JoinPoint joinPoint) {
        System.out.println("" + joinPoint.getSignature().getName() + "结束。。。@After");
    }

    /**
     * JoinPoint一定要出现在参数表的第一位
     */
    @AfterReturning(value = "pointCut()", returning = "result")
    public void logReturn(JoinPoint joinPoint, Object result) {
        System.out.println("" + joinPoint.getSignature().getName() +
                "正常返回。。。@AfterReturning:运行结果：{" + result + "}");
    }

    @AfterThrowing(value = "pointCut()", throwing = "exception")
    public void logException(JoinPoint joinPoint, Exception exception) {
        System.out.println("" + joinPoint.getSignature().getName() +
                "异常。。。异常信息：{" + exception + "}");
    }
}

```

（4）配置类

```java
@EnableAspectJAutoProxy
@Configuration
public class MainConfigOfAOP {

	/** 业务逻辑类加入容器中 */
	@Bean
	public MathCalculator calculator(){
		return new MathCalculator();
	}

	/** 切面类加入到容器中 */
	@Bean
	public LogAspects logAspects(){
		return new LogAspects();
	}
}
```

（5）测试类

```java
@Test
    public void f() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        MathCalculator mathCalculator = context.getBean(MathCalculator.class);
        int div = mathCalculator.div(1, 2);
        System.out.println(div);
        context.close();
    }
```

#### 复习

略

#### 源码走读

### 事务

#### 入门

（1）pom文件

```xml
        <!-- 数据库相关依赖===start -->
        <dependency>
            <groupId>org.springframework</groupId>
            <artifactId>spring-jdbc</artifactId>
            <version>${spring.version}</version>
        </dependency>

        <dependency>
            <groupId>mysql</groupId>
            <artifactId>mysql-connector-java</artifactId>
            <version>${mysql.version}</version>
        </dependency>
        <!-- 数据库相关依赖===end -->
```

（2）数据库配置

使用的是本地数据库 mysql，版本：8.0.18，数据库：trans

表：bank

| 列 | 类型 |
| --- | --- |
| id | int |
| customer | varchar |
| currentMoney | decimal |


（3）创建对应实体类

```java
@Data
@NoArgsConstructor
@AllArgsConstructor
public class Customer {
    private Integer id;
    private String customerName;
    private Integer currentMoney;
}
```

（4）创建 DAO

```java
@Component
public class CustomerDao {

    @Autowired
    private JdbcTemplate jdbcTemplate;

    public void insert(Customer customer) {
        String sql = "insert into bank(customerName, currentMoney) values (?, ?)";
        jdbcTemplate.update(sql, customer.getCustomerName(), customer.getCurrentMoney());
    }
}
```

（5）创建 Service

```java
@Service
public class CustomerService {

    @Autowired
    private CustomerDao customerDao;

    @Transactional
    public void insert(Customer customer) {
        System.out.println("开始插入...");
        customerDao.insert(customer);
        int i = 1/0;
        System.out.println("插入成功");
    }
}
```

（6）创建配置类（无事务）

```java
@ComponentScan(basePackages = "suncodes.opensource.tx")
@Configuration
public class TxConfig {

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/trans?serverTimezone=UTC");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        return driverManagerDataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }
}
```

（7）测试（无事务）

```java
    @Test
    public void f() {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(TxConfig.class);
        CustomerService customerService = context.getBean(CustomerService.class);
        Customer customer = new Customer();
        customer.setCustomerName("sunchuizhe");
        customer.setCurrentMoney(200);
        customerService.insert(customer);
    }
```

（8）添加事务

```java
@EnableTransactionManagement
@ComponentScan(basePackages = "suncodes.opensource.tx")
@Configuration
public class TxConfig {

    @Bean
    DataSource dataSource() {
        DriverManagerDataSource driverManagerDataSource = new DriverManagerDataSource();
        driverManagerDataSource.setDriverClassName("com.mysql.cj.jdbc.Driver");
        driverManagerDataSource.setUrl("jdbc:mysql://localhost:3306/trans?serverTimezone=UTC");
        driverManagerDataSource.setUsername("root");
        driverManagerDataSource.setPassword("root");
        return driverManagerDataSource;
    }

    @Bean
    JdbcTemplate jdbcTemplate(DataSource dataSource) {
        JdbcTemplate jdbcTemplate = new JdbcTemplate();

        jdbcTemplate.setDataSource(dataSource);
        return jdbcTemplate;
    }

    @Bean
    public PlatformTransactionManager transactionManager(DataSource dataSource) throws Exception{
        return new DataSourceTransactionManager(dataSource);
    }
}
```

（9）测试（有事务）

同上

其他：

（1）控制台乱码

打开IntelliJ IDEA>File>Setting>Editor>File Encodings，将Global Encoding、Project Encoding 设置 UTF8

（2）有关 @Transactional 加在哪儿？

目前的结构是这样的；

service --> dao --> 调用 JDBCTemplate

a）在service加@Transactional，且抛出异常，有效果

b）在service加@Transactional，在dao抛异常，有效果

c）在dao加@Transactional，在service抛出异常，没有效果

d）在dao加@Transactional，在dao抛出异常，有效果

所以，可以看出只要是在加了@Transactional的方法，内部出现异常以及有JDBC操作，都会进行回滚。

加了@Transactional的方法，只有效于整个方法，以及方法内部调用的其他所有方法（默认其他方法没有其他@Transactional特殊处理）

#### 复习
略

#### 原理

- 要开启Spring事务需要添加注解@EnableTransactionManagement

  - 通过注解的属性名称可以猜测属性的作用，具体逻辑在后续的代码实现中进行分析。通过这个注解可以看到是@Import(TransactionManagementConfigurationSelector.class)了一个类，因此直接看这个类。

  - 在这个类中的selectImports方法，获取类上泛型类型（即EnableTransactionManagement），从当前正在注入的bean中的所有注解中获取EnableTransactionManagement注解的属性，从EnableTransactionManagement注解的属性获取mode属性值，根据不同的配置去注入不同的bean实现。

  - 在注入容器registerBeanDefinitions时，当容器中不存在org.springframework.aop.config.internalAutoProxyCreator的时候默认注入InfrastructureAdvisorAutoProxyCreator的bean。

- InfrastructureAdvisorAutoProxyCreator 则属于AOP的范畴了，这里暂时忽略其逻辑。

  - 涉及到 AOP，就涉及到切面（切点+通知）和目标方法

  - 事务的advisor是BeanFactoryTransactionAttributeSourceAdvisor

  - 在 ProxyTransactionManagementConfiguration中，注入了BeanFactoryTransactionAttributeSourceAdvisor

  - Spring的Advisor其实是组合了Pointcut和Advice而他们的具体实现分别是AnnotationTransactionAttributeSource和TransactionInterceptor。

- TransactionAttributeSource虽然不是一个Pointcut，但是它被Pointcut所用，用于检测一个类的方法上是否有@Transactional注解，来确定该方法是否需要事物增强。

  - TransactionAttributeSourcePointcut类以Pointcut结尾，说明它是一个切入点，就是标识要被拦截的方法。类名的前缀部分表明了这个切入点的实现原理。

  - 看下这个前缀是TransactionAttributeSource，它以Source结尾，说明它是一个源（即源泉，有向外提供东西的意思）。它的前缀是TransactionAttribute，即事务属性。

  - 由此可见，这个源可以向外提供事务属性，其实就是判断一个类的方法上是否标有@Transactional注解，如果有的话还可以获取这个注解的属性（即事务属性）。

  - 整体来说就是，Pointcut拦截住了方法，然后使用这个“源”去方法和类上获取事务属性，如果能获取到，说明此方法需要参与事务，则进行事务增强，反之则不增强。

- Advice就是AOP中的增强，TransactionInterceptor实现了Advice接口，所以它就是事务增强。

  - TransactionInterceptor类调用的invokeWithinTransaction方法
  
  - 前两行获取事务属性“源”，再用这个“源”来获取事务属性。咦，有点奇怪，上面不是已经获取过了吗？是的，上面是在Pointcut里获取的，那只是用于判断那个方法是否要被拦截而已。这里获取的属性才是真正用于事务的。
    
    第三行是根据事务属性，来确定出一个事务管理器来。
    
    接下来是使用事务管理器打开事务。
    
    接下来是对被拦截住的目标方法的调用执行，当然要try/catch住这个执行。
    
    如果抛出了异常，则进行和异常相关的事务处理，然后将这个异常继续向上抛出。
    
    如果没有抛出异常，则进行事务提交。
    
    最后的else分支是对编程式事务的调用，事务的打开/提交/回滚是开发人员自己写代码控制，所以就不需要事务管理器操心了。


    BeanFactoryTransactionAttributeSourceAdvisor：封装了实现事务所需的所有属性，包括Pointcut，Advice，TransactionManager以及一些其他的在Transactional注解中声明的属性；

    TransactionAttributeSourcePointcut：用于判断哪些bean需要织入当前的事务逻辑。这里可想而知，其判断的基本逻辑就是判断其方法或类声明上有没有使用@Transactional注解，如果使用了就是需要织入事务逻辑的bean;

    TransactionInterceptor：这个bean本质上是一个Advice，其封装了当前需要织入目标bean的切面逻辑，也就是Spring事务是如果借助于数据库事务来实现对目标方法的环绕的。




#### 源码

基于SpringBoot的Spring事务实现原理

https://blog.csdn.net/jy00733505/article/details/107240787/

### BeanFactoryPostProcessor

#### 入门

（1）继承 BeanFactoryPostProcessor，自定义实现类

```java
@Component
public class MyBeanFactoryPostProcessor implements BeanFactoryPostProcessor {

	@Override
	public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
		System.out.println("MyBeanFactoryPostProcessor...postProcessBeanFactory...");
		int count = beanFactory.getBeanDefinitionCount();
		String[] names = beanFactory.getBeanDefinitionNames();
		System.out.println("当前BeanFactory中有 " + count + " 个Bean");
		for (String name : names) {
			System.out.println(name);
		}
	}
}
```

（2）配置成Bean

```java
@ComponentScan(basePackages = "suncodes.opensource.ext")
@Configuration
public class ExtConfig {
}

```

（3）测试

```java
    @Test
    public void myBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext context  =
                new AnnotationConfigApplicationContext(ExtConfig.class);
        context.close();
    }
```

#### 源码

```
BeanFactoryPostProcessor原理:
1)、ioc容器创建对象
2)、invokeBeanFactoryPostProcessors(beanFactory);
		如何找到所有的BeanFactoryPostProcessor并执行他们的方法；
			1）、直接在BeanFactory中找到所有类型是BeanFactoryPostProcessor的组件，并执行他们的方法
			2）、在初始化创建其他组件前面执行

2、BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
		postProcessBeanDefinitionRegistry();
		在所有bean定义信息将要被加载，bean实例还未创建的；
		优先于BeanFactoryPostProcessor执行；
		利用BeanDefinitionRegistryPostProcessor给容器中再额外添加一些组件；
	原理：
		1）、ioc创建对象
		2）、refresh()-》invokeBeanFactoryPostProcessors(beanFactory);
		3）、从容器中获取到所有的BeanDefinitionRegistryPostProcessor组件。
			1、依次触发所有的postProcessBeanDefinitionRegistry()方法
			2、再来触发postProcessBeanFactory()方法BeanFactoryPostProcessor；
		4）、再来从容器中找到BeanFactoryPostProcessor组件；然后依次触发postProcessBeanFactory()方法

```

### BeanDefinitionRegistryPostProcessor

```java
@Component
public class MyBeanDefinitionRegistryPostProcessor implements BeanDefinitionRegistryPostProcessor {
    /**
     * 先执行
     * 此处是用于注册BeanDefinition的
     * BeanDefinitionRegistry Bean定义信息的保存中心，
     * 以后BeanFactory就是按照BeanDefinitionRegistry里面保存的每一个bean定义信息创建bean实例；
     */
    @Override
    public void postProcessBeanDefinitionRegistry(BeanDefinitionRegistry registry) throws BeansException {
        System.out.println("============= MyBeanDefinitionRegistryPostProcessor.postProcessBeanDefinitionRegistry ============");
        String[] beanDefinitionNames = registry.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        RootBeanDefinition rootBeanDefinition = new RootBeanDefinition();
        rootBeanDefinition.setBeanClass(ExtBean.class);
        registry.registerBeanDefinition("hehe", rootBeanDefinition);
    }

    /**
     * 后执行
     * 此处是用于注册Bean的
     * 注册bean之前需要有bean的描述信息BeanDefinition
     * @param beanFactory
     * @throws BeansException
     */
    @Override
    public void postProcessBeanFactory(ConfigurableListableBeanFactory beanFactory) throws BeansException {
        System.out.println("============= MyBeanDefinitionRegistryPostProcessor.postProcessBeanFactory ============");
        String[] beanDefinitionNames = beanFactory.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        // 在调用registerSingleton之前不能调用beanFactory.getBean("hehe")，否则报错
        // Could not register object [ExtBean(msg=这是我定义的Bean)] under bean name 'hehe': there is already object [ExtBean(msg=null)] bound
        // 说Bean的名字已经和object对象进行绑定了
        beanFactory.registerSingleton("hehe", new ExtBean("这是我定义的Bean"));
        System.out.println(beanFactory.getBean("hehe"));
    }
}
```

### ApplicationListener

https://blog.csdn.net/liyantianmin/article/details/81017960

https://blog.csdn.net/baidu_19473529/article/details/97646739

（1）入门

a）自定义事件

```java
/**
 * 自定义事件
 * 应该就是通过不同的 ApplicationEvent 触发不同的事件，之后在监听器中处理不同的逻辑
 * 那么触发的时机怎么控制？在不同的阶段，进行不同时间的调用，进行不同时间的触发
 */
@Getter
@Setter
@ToString
public class MyApplicationEvent extends ApplicationEvent {

    private String address;
    private String text;

    public MyApplicationEvent(Object source, String address, String text) {
        super(source);
        this.address = address;
        this.text = text;
    }

    public MyApplicationEvent(Object source) {
        super(source);
    }
}

```

b）自定义监听器

```java
@Component
public class MyApplicationListener implements ApplicationListener {
    @Override
    public void onApplicationEvent(ApplicationEvent event) {
        if (event instanceof MyApplicationEvent) {
            // 不主动触发 context.publishEvent(myApplicationEvent) 不会被调用
            MyApplicationEvent emailEvent = (MyApplicationEvent) event;
            System.out.println("邮件地址：" + emailEvent.getAddress());
            System.out.println("邮件内容：" + emailEvent.getText());
        } else {
            System.out.println("容器本身事件：" + event);
        }
    }
}
```

c）触发事件

```java
    @Test
    public void myBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(ExtConfig.class);
        // 创建一个ApplicationEvent对象
        MyApplicationEvent event = new MyApplicationEvent("hello", "abc@163.com", "This is a test");
        // 主动触发该事件
        context.publishEvent(event);
        context.close();
    }
```

（2）@EventListener入门

```java
@Component
public class MyAtEventListener {

    /**
     * 注解方式和继承ApplicationListener方式的明显区别：
     * 注解方式可以指定监听某个事件，其他事件是不需要关注的
     */
    @EventListener(classes = {MyApplicationEvent.class})
    public void f() {
        System.out.println(">>>>>>>>>>>>>>>>>>>");
    }
}

```

（3）源码

```
3、ApplicationListener：监听容器中发布的事件。事件驱动模型开发；
	  public interface ApplicationListener<E extends ApplicationEvent>
		监听 ApplicationEvent 及其下面的子事件；

	 步骤：
		1）、写一个监听器（ApplicationListener实现类）来监听某个事件（ApplicationEvent及其子类）
			@EventListener;
			原理：使用EventListenerMethodProcessor处理器来解析方法上的@EventListener；

		2）、把监听器加入到容器；
		3）、只要容器中有相关事件的发布，我们就能监听到这个事件；
				ContextRefreshedEvent：容器刷新完成（所有bean都完全创建）会发布这个事件；
				ContextClosedEvent：关闭容器会发布这个事件；
		4）、发布一个事件：
				applicationContext.publishEvent()；
	
 原理：
 	ContextRefreshedEvent、IOCTest_Ext$1[source=我发布的时间]、ContextClosedEvent；
 1）、ContextRefreshedEvent事件：
 	1）、容器创建对象：refresh()；
 	2）、finishRefresh();容器刷新完成会发布ContextRefreshedEvent事件
 2）、自己发布事件；
 3）、容器关闭会发布ContextClosedEvent；
 
 【事件发布流程】：
 	3）、publishEvent(new ContextRefreshedEvent(this));
 			1）、获取事件的多播器（派发器）：getApplicationEventMulticaster()
 			2）、multicastEvent派发事件：
 			3）、获取到所有的ApplicationListener；
 				for (final ApplicationListener<?> listener : getApplicationListeners(event, type)) {
 				1）、如果有Executor，可以支持使用Executor进行异步派发；
 					Executor executor = getTaskExecutor();
 				2）、否则，同步的方式直接执行listener方法；invokeListener(listener, event);
 				 拿到listener回调onApplicationEvent方法；
 
 【事件多播器（派发器）】
 	1）、容器创建对象：refresh();
 	2）、initApplicationEventMulticaster();初始化ApplicationEventMulticaster；
 		1）、先去容器中找有没有id=“applicationEventMulticaster”的组件；
 		2）、如果没有this.applicationEventMulticaster = new SimpleApplicationEventMulticaster(beanFactory);
 			并且加入到容器中，我们就可以在其他组件要派发事件，自动注入这个applicationEventMulticaster；
 
 【容器中有哪些监听器】
 	1）、容器创建对象：refresh();
 	2）、registerListeners();
 		从容器中拿到所有的监听器，把他们注册到applicationEventMulticaster中；
 		String[] listenerBeanNames = getBeanNamesForType(ApplicationListener.class, true, false);
 		//将listener注册到ApplicationEventMulticaster中
  		getApplicationEventMulticaster().addApplicationListenerBean(listenerBeanName);

```

（4）@EventListener源码

```
SmartInitializingSingleton 原理：->afterSingletonsInstantiated();
		1）、ioc容器创建对象并refresh()；
		2）、finishBeanFactoryInitialization(beanFactory);初始化剩下的单实例bean；
			1）、先创建所有的单实例bean；getBean();
			2）、获取所有创建好的单实例bean，判断是否是SmartInitializingSingleton类型的；
				如果是就调用afterSingletonsInstantiated();
```












































