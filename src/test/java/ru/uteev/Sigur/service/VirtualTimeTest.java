package ru.uteev.Sigur.service;

import org.junit.jupiter.api.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import ru.uteev.Sigur.dao.Employee;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;

@SpringBootTest
class VirtualTimeTest {
    final static Logger logger= LoggerFactory.getLogger(VirtualTimeTest.class);

    @Autowired
    private VirtualTime virtualTime;

    @Autowired
    private EmployeeManager employeeManager;

    @Autowired
    private GuestManager guestManager;


    @Test
    void getDay() {
        Date now = virtualTime.getNeededDateWithRandomMinInThisDay(233);
        logger.info(now.toString());
    }

    @Test
    void tt() {
        Calendar startTime = new GregorianCalendar(2022, 0, 1);
        startTime.add(Calendar.DATE, 233);
        startTime.add(Calendar.MINUTE, 14);
        logger.info("test time= "+startTime.getTime().toString());
    }

    @Test
    void getRandomVisitDate() {
        Employee employee = employeeManager.getEmployee();
        Date random = virtualTime.getRandomVisitDateAfterHire(employee);
        if (employee.getHireTime().after(random)) {
            logger.info("HireTime =" + employee.getHireTime());
            logger.info("visitDate = " + random.toString());
        } else {
            logger.info("ОШИБКА");
            logger.info("HireTime =" + employee.getHireTime());
            logger.info("visitDate = " + random.toString());
        }
    }
}