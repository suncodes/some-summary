package suncodes.opensource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import suncodes.opensource.aop.MainConfigOfAOP;
import suncodes.opensource.aop.MathCalculator;

public class AOPTest {

    @Test
    public void f() {
        AnnotationConfigApplicationContext context =
                new AnnotationConfigApplicationContext(MainConfigOfAOP.class);
        MathCalculator mathCalculator = context.getBean(MathCalculator.class);
        int div = mathCalculator.div(1, 2);
        System.out.println(div);
        context.close();
    }
}
