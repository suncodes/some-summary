package suncodes.opensource.lifecycle;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        // initMethod = "init" 被调用
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(LifeCycleConfig.class);
        // destroyMethod = "destroy" 被调用
        context.close();
    }
}
