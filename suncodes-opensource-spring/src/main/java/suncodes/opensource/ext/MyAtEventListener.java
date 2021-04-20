package suncodes.opensource.ext;

import org.springframework.context.event.EventListener;
import org.springframework.stereotype.Component;

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
