import dao.EmployeeDao;
import org.junit.Test;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import pojo.po.Employee;

public class EmployeeTest {

    @Test
    public void f() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");
        EmployeeDao employeeDao = context.getBean("employeeDao", EmployeeDao.class);

        Employee employee = employeeDao.getById(1);
        System.out.println(employee);
    }

    @Test
    public void f1() {
        ClassPathXmlApplicationContext context =
                new ClassPathXmlApplicationContext("application.xml");
        EmployeeDao employeeDao = context.getBean("employeeDao", EmployeeDao.class);

        Employee employee = new Employee();
        employee.setName("sunchuizhe");
        employee.setSalary(1000.0F);
        employeeDao.saveEmployee(employee);
    }
}
