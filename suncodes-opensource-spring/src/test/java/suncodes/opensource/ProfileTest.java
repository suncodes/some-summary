package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.profile.MyProfileConfig;

public class ProfileTest {
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

    @Test
    public void f1() {
        // 1、创建一个applicationContext
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext();
        // 2、设置需要激活的环境
        context.getEnvironment().setActiveProfiles("dev");
        // 3、注册主配置类
        context.register(MyProfileConfig.class);
        // 4、启动刷新容器
        context.refresh();
        String[] beanDefinitionNames = context.getBeanDefinitionNames();
        for (String beanDefinitionName : beanDefinitionNames) {
            System.out.println(beanDefinitionName);
        }
        context.close();
    }
}
