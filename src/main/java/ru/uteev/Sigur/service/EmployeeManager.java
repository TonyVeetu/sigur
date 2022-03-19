package ru.uteev.Sigur.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.uteev.Sigur.dao.Employee;
import ru.uteev.Sigur.dao.TYPE;
import ru.uteev.Sigur.repos.EmployeeDAO;

import java.util.Date;
import java.util.List;

@Component
public class EmployeeManager {
    final static Logger logger= LoggerFactory.getLogger(EmployeeManager.class);

    public static int countsOfDepartment = 10;
    private int countForFire = 0;
    @Autowired
    private VirtualTime virtualTime;

    @Autowired
    private EmployeeDAO employeeDAO;

    @Autowired
    private PassEmulator passEmulator;

    private Integer[] departId  = new Integer[] {101, 102, 103, 104, 105, 106, 107, 108, 109, 110 };
    private String[] departName  = new String[] {"aaa", "bbb", "ccc", "ddd", "eee", "fff", "jjj", "hhh", "kkk", "uuuu"  };

    public Employee getEmployee() {
        int currentDay = virtualTime.getCurrentDay();
        int randomDay = (int) (currentDay + Math.random() * VirtualTime.countDayInYear);
        Date dateHire = virtualTime.getNeededDateWithRandomMinInThisDay(randomDay);
        int randDepIndex = (int) (Math.random()*countsOfDepartment);
        return new Employee(TYPE.EMPLOYEE, dateHire, null, departId[randDepIndex], departName[randDepIndex]);
    }

    public void work() {
        countForFire++;
        if (countForFire < 5) {
            Employee employee = getEmployee();

            employeeDAO.save(employee);
            Date currentDate = virtualTime.getCurrentDate();
            logger.error(String.valueOf(countForFire));
            logger.info("Текущая дата = " + currentDate.toString()
                + ". Сотрудник (" + employee.getId() + ")"
                + " нанят (" + employee.getHireTime() + ")"
                + ", Отдел (" + employee.getDepartmentName() + ")");
        } else {
            countForFire = 0;
            fireRandomCountOfEmployee();
        }

    }

    private void fireRandomCountOfEmployee() {
        List<Employee> dontFire = employeeDAO.findAllByFireTimeIsNull();
        int length = dontFire.size();
        int randomCountOfFireEmployee = (int) (1 + Math.random() * 3);

        int j = 0;
        while ( j < randomCountOfFireEmployee) {
            Employee employeeForFire = dontFire.get(j);
            j++;
            //todo correct
            Date fireDate = virtualTime.getCurrentDate();
            Date hireDate = employeeForFire.getHireTime();
            int countOfWorkDays = virtualTime.getDifferentInDayBetweenDate(employeeForFire.getHireTime(), fireDate);
            if (fireDate.after(hireDate)) {
                employeeForFire.setFireTime(fireDate);
                employeeDAO.save(employeeForFire);
                Date currentDate = virtualTime.getCurrentDate();
                logger.info("Дата = " + currentDate
                        + ". Сотрудник (" + employeeForFire.getId()
                        + ") уволен, дата увольнения: " + fireDate +
                        ". Отдел " + employeeForFire.getDepartmentName()
                        + ". Проработал дней: " + countOfWorkDays);
            } else {
                logger.info("Сотрудник " + employeeForFire.toString() +
                        " еще не нанят на работу, поэтому его не возможно уволить! Текущая дата = " + fireDate.toString());
            }
        }
    }

}
