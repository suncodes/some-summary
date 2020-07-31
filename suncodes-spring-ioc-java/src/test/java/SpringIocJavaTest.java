import annotation.JavaOneBean;
import bean.IntegerBean;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class SpringIocJavaTest {

    @Test
    public void f() {
        AnnotationConfigApplicationContext config =
                new AnnotationConfigApplicationContext("config");
        IntegerBean integerBean = config.getBean("integerBean", IntegerBean.class);
        System.out.println(integerBean);
        JavaOneBean javaOneBean = config.getBean("javaOneBean", JavaOneBean.class);
        System.out.println(javaOneBean);
        System.out.println(config.containsBean("aFloat"));
    }
}
