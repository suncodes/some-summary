package suncodes.opensource.ext;

import org.springframework.context.ApplicationEvent;
import org.springframework.context.ApplicationListener;
import org.springframework.stereotype.Component;

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
