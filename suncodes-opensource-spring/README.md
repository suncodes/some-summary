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


#### @Resource


#### @Inject


#### 自动装配原理



































