package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.ext.ExtConfig;
import suncodes.opensource.ext.MyApplicationEvent;

public class ExtTest {

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
}
