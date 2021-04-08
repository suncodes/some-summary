package suncodes.opensource.propertyvalue;

import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class Test {
    public static void main(String[] args) {
        AnnotationConfigApplicationContext context = new AnnotationConfigApplicationContext(PropertyValueConfig.class);
        Person person = context.getBean("person", Person.class);
        System.out.println(person);
        context.close();
    }
}
