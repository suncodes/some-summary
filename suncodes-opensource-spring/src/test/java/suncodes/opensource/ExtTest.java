package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.ext.ExtConfig;

public class ExtTest {

    @Test
    public void myBeanFactoryPostProcessor() {
        AnnotationConfigApplicationContext context  =
                new AnnotationConfigApplicationContext(ExtConfig.class);
        context.close();
    }
}
