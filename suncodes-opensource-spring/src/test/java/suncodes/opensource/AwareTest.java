package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.aware.AwareConfig;

public class AwareTest {

    @Test
    public void f() {
        AnnotationConfigApplicationContext context  =
                new AnnotationConfigApplicationContext(AwareConfig.class);
        context.close();
    }
}
