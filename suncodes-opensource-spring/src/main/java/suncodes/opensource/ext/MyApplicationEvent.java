package suncodes.opensource.ext;

import lombok.Getter;
import lombok.Setter;
import lombok.ToString;
import org.springframework.context.ApplicationEvent;

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
