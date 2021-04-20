package suncodes.opensource.ext;

import org.springframework.beans.BeansException;
import org.springframework.beans.factory.config.ConfigurableListableBeanFactory;
import org.springframework.beans.factory.support.BeanDefinitionRegistry;
import org.springframework.beans.factory.support.BeanDefinitionRegistryPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.stereotype.Component;

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
