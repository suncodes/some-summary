package dao;

import org.springframework.orm.hibernate5.support.HibernateDaoSupport;
import org.springframework.transaction.annotation.Transactional;
import pojo.po.Employee;

import java.util.ArrayList;
import java.util.List;

@Transactional
public class EmployeeDao extends HibernateDaoSupport {

    /**
     * method to save employee
     */
    public void saveEmployee(Employee e) {
        getHibernateTemplate().save(e);
    }

    /**
     * method to update employee
     */
    public void updateEmployee(Employee e) {
        getHibernateTemplate().update(e);
    }

    /**
     * method to delete employee
     */
    public void deleteEmployee(Employee e) {
        getHibernateTemplate().delete(e);
    }

    /**
     * method to return one employee of given id
     */
    public Employee getById(int id) {
        Employee e = getHibernateTemplate().get(Employee.class, id);
        return e;
    }

    /**
     * method to return all employees
     */
    public List<Employee> getEmployees() {
        List<Employee> list = new ArrayList<Employee>();
        list = getHibernateTemplate().loadAll(Employee.class);
        return list;
    }
}
