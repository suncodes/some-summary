package suncodes;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import suncodes.aop.java.service.JavaAopService;
import suncodes.aop.xml.service.XmlAopService;

@SpringBootTest
@RunWith(SpringJUnit4ClassRunner.class)
public class SpringBootAopApplicationTest {

    @Autowired
    private XmlAopService xmlAopService;

    @Autowired
    private JavaAopService javaAopService;

    @Test
    public void f() {
        xmlAopService.xmlAopFunction();
    }

    @Test
    public void f1() {
        javaAopService.f();
    }
}
